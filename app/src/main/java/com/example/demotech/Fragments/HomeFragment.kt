package com.example.demotech.Fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.demotech.BackPressedActivty
import com.example.demotech.Pdf_Detials_Actvity
import com.example.demotech.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var btn_openpdf: Button
    private lateinit var tvSelectedDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        btn_openpdf=view.findViewById(R.id.btn_openpdf)
        btn_openpdf.setOnClickListener{
            val int=Intent(requireContext(),BackPressedActivty::class.java)
            startActivity(int)
        }


        return view
    }

}
