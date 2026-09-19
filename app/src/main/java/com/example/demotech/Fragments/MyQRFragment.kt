package com.example.demotech.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demotech.MainActivity
import com.example.demotech.R


class MyQRFragment : Fragment() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_q_r, container, false)
    }

    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode=true
        requireView().requestFocus()
        requireView().setOnKeyListener(View.OnKeyListener{v, keyCode, event ->
            if (event.action== KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK
                ){
                val  int= Intent(requireContext(),MainActivity::class.java)
                startActivity(int)
                requireActivity().overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
                requireActivity().finish()
                return@OnKeyListener true
            }
            false

        })
    }
}