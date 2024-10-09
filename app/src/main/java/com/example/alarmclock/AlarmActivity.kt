package com.example.alarmclock

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity : AppCompatActivity() {

    private lateinit var textViewMessage: TextView
    private lateinit var btnStopAlarm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        textViewMessage = findViewById(R.id.textViewMessage)
        btnStopAlarm = findViewById(R.id.btnStopAlarm)

        textViewMessage.text = getString(R.string.wake_up)

        btnStopAlarm.setOnClickListener {
            stopRingtone()
            finish()
        }
    }

    private fun stopRingtone() {
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone: Ringtone = RingtoneManager.getRingtone(applicationContext, alarmUri)
        if (ringtone.isPlaying) {
            ringtone.stop()
        }
    }
}
