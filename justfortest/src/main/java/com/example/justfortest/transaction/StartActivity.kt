package com.example.justfortest.transaction

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.SharedElementCallback
import androidx.core.view.ViewCompat
import com.example.justfortest.R
import kotlinx.android.synthetic.main.activity_anim_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim_start)
        start.setOnClickListener {
            ActivityCompat.setExitSharedElementCallback(this, object : SharedElementCallback() {
                override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
                    super.onMapSharedElements(names, sharedElements)
                }
            });


            val intent = Intent(this, EndActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, start, ViewCompat.getTransitionName(start)!!);
            ActivityCompat.startActivity(this, intent, options.toBundle());
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        println("onActivityReenter")
    }
}