package com.example.justfortest.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.justfortest.R

class FragmentB : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("FragmentB onCreateView")
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onAttach(context: Context?) {
        println("FragmentB onAttach")
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        println("FragmentB onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        println("FragmentB onStart")
        super.onStart()
    }

    override fun onResume() {
        println("FragmentB onResume")
        super.onResume()
    }

    override fun onPause() {
        println("FragmentB onPause")
        super.onPause()
    }

    override fun onStop() {
        println("FragmentB onStop")
        super.onStop()
    }

    override fun onDestroy() {
        println("FragmentB onDestroy")
        super.onDestroy()
    }
}