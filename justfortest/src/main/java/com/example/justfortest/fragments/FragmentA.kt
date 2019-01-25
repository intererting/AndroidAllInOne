package com.example.justfortest.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.justfortest.R
import kotlinx.android.synthetic.main.fragment_a.*

class FragmentA : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("FragmentA onCreateView")
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onAttach(context: Context?) {
        println("FragmentA onAttach")
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        println("FragmentA onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        println("FragmentA onStart")
        super.onStart()
    }

    override fun onResume() {
        println("FragmentA onResume")
        super.onResume()
    }

    override fun onPause() {
        println("FragmentA onPause")
        super.onPause()
    }

    override fun onStop() {
        println("FragmentA onStop")
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("FragmentA onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        println("FragmentA onDestroyView")
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        println("onSaveInstanceState onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }
}