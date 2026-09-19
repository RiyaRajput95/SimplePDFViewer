
package com.example.demotech

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class Pdf_Detials_Actvity : AppCompatActivity() {

    private val pdfUrl = "https://www.orimi.com/pdf-test.pdf"
    private val fileName: String by lazy {
        Uri.parse(pdfUrl).lastPathSegment ?: "downloaded_file.pdf"
    }

    private var downloadId: Long = 0L
    private lateinit var titleText: TextView
    private lateinit var pdfView: PDFView
    private lateinit var openPdfButton: Button
    private var downloadedFile: File? = null

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_detials_actvity)

        titleText = findViewById(R.id.titleText)
        pdfView = findViewById(R.id.pdfView)
        openPdfButton = findViewById(R.id.openPdfButton)

        titleText.text = "Best CAT Preparation Notes"

        downloadedFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)

        // Disable button until PDF is downloaded
        openPdfButton.isEnabled = false

        // If the file already exists, open it
        if (downloadedFile!!.exists()) {
            titleText.text = "PDF Ready! Tap to open."
            openPdfButton.isEnabled = true
        } else {
            downloadPdf()
        }

        openPdfButton.setOnClickListener {
            if (downloadedFile!!.exists()) {
                openPdf(downloadedFile!!)
            } else {
                titleText.text = "PDF not downloaded yet. Please wait..."
            }
        }

        // Register download complete receiver
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                Context.RECEIVER_EXPORTED
            )
        } else {
            registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
        }
    }

    private fun downloadPdf() {
        // Delete any old file with same name
        downloadedFile?.let {
            if (it.exists()) {
                it.delete()
            }
        }

        val request = DownloadManager.Request(Uri.parse(pdfUrl))
            .setTitle("Downloading PDF")
            .setDescription("Please wait...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, fileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadId = manager.enqueue(request)
    }

    private val onDownloadComplete = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadId) {
                downloadedFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
                if (downloadedFile!!.exists()) {
                    titleText.text = "PDF Downloaded! Tap the button to open."
                    openPdfButton.isEnabled = true
                }
            }
        }
    }

    private fun openPdf(file: File) {
        Log.d("PDF_DEBUG", "Opening file: ${file.absolutePath}")

        pdfView.fromFile(file)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .load()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onDownloadComplete)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
