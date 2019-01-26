package com.yuliyang.mediatest

import android.Manifest
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val recorde = AudioRecorder()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.isSpeakerphoneOn = false
        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0,
                AudioManager.STREAM_VOICE_CALL)
        audioManager.mode = AudioManager.MODE_IN_CALL

        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 100)
        setContentView(R.layout.activity_main)
        start.setOnClickListener {
            recorde.startAudioRecording()
        }
        stop.setOnClickListener {
            recorde.stopAudioRecording()
        }
    }
}
