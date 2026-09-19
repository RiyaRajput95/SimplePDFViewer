package com.example.demotech

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.demotech.Fragments.HomeFragment
import com.example.demotech.Fragments.MyBookingsFragment
import com.example.demotech.Fragments.MyQRFragment
import com.example.demotech.Fragments.ProfileFragment
import com.example.demotech.Fragments.ScanqrFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var floatingicon: ImageView
    private lateinit var BottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatingicon=findViewById(R.id.floating_icon)
        BottomNavigationView=findViewById(R.id.bottomnavigation)
        fragmentReplacement(HomeFragment())
        BottomNavigationView.setOnItemSelectedListener { item ->
            moveFloatingIconTo(item.itemId)
            true
        }
        BottomNavigationView.selectedItemId=R.id.home
        floatingicon.post{
            moveFloatingIconTo(R.id.home)
        }

    }

    private fun moveFloatingIconTo(itemId: Int) {
        val menu=BottomNavigationView.menu
        val index = (0 until menu.size()).indexOfFirst { menu.getItem(it).itemId==itemId }
        if (index== -1)
            return
        val tabWidth = BottomNavigationView.width / menu.size()
        val targetX = tabWidth * index + tabWidth / 2 - floatingicon.width / 2

        floatingicon.animate()
            .x(targetX.toFloat())
            .setDuration(250)
            .start()

        when (itemId){
            R.id.home ->{
                floatingicon.setImageResource(R.drawable.home_flot_icon)
                fragmentReplacement(HomeFragment())
            }
            R.id.my_bookings ->{
                floatingicon.setImageResource(R.drawable.profile_float)
                fragmentReplacement(MyBookingsFragment())
            }
            R.id.profile ->{
                floatingicon.setImageResource(R.drawable.profile_float)
                fragmentReplacement(ProfileFragment())
            }
            R.id.scan_qr ->{
                floatingicon.setImageResource(R.drawable.scan_float)
                fragmentReplacement(ScanqrFragment())
            }
            R.id.my_qr ->{
                floatingicon.setImageResource(R.drawable.myqr_float)
                fragmentReplacement(MyQRFragment())
            }
        }
    }

    private fun fragmentReplacement(fragment: Fragment){
        val frgtran: FragmentTransaction=supportFragmentManager.beginTransaction()
        frgtran.replace(R.id.frame_main,fragment)
        frgtran.commit()
    }
}