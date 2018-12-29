package com.example.justfortest.nestscroll

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justfortest.R
import kotlinx.android.synthetic.main.activity_nest_test.*

class NestTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nest_test)
        with(recyclerview) {
            layoutManager = LinearLayoutManager(this@NestTestActivity)
            adapter = TestAdapter(this@NestTestActivity)
        }
    }

    class TestAdapter(val mContext: Context) : RecyclerView.Adapter<TestHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
            return TestHolder(LayoutInflater.from(mContext).inflate(R.layout.item_test, parent, false))
        }

        override fun getItemCount(): Int {
            return 50
        }

        override fun onBindViewHolder(holder: TestHolder, position: Int) {
        }

    }

    class TestHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}