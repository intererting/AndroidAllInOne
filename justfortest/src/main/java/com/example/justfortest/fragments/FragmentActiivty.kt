package com.example.justfortest.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.justfortest.R
import kotlinx.android.synthetic.main.activity_fragments.*
import kotlinx.android.synthetic.main.fragment_a.*

class FragmentActiivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments)
        val fragment = supportFragmentManager.findFragmentByTag("fragmentA")
        if (fragment == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, FragmentA(), "fragmentA").commit()
        }
        startB.setOnClickListener {
            supportFragmentManager.beginTransaction().addToBackStack("fragmentB")
                    .replace(R.id.container, FragmentB()).commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}