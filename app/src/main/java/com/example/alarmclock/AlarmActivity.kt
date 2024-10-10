package com.example.alarmclock

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import pl.droidsonroids.gif.GifImageView

class AlarmActivity : AppCompatActivity() {

    private lateinit var textViewMessage: TextView
    private lateinit var btnStopAlarm: Button
    private lateinit var gifImageView: GifImageView
    private lateinit var ringtone: Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        textViewMessage = findViewById(R.id.textViewMessage)
        btnStopAlarm = findViewById(R.id.btnStopAlarm)
        gifImageView = findViewById(R.id.gifIV)

        textViewMessage.text = getString(R.string.wake_up)

        val alarmUri: Uri? = intent.getParcelableExtra("ALARM_URI")
        if (alarmUri != null) {
            ringtone = RingtoneManager.getRingtone(this, alarmUri)
            showGifAndPlayRingtone()
        }

        btnStopAlarm.setOnClickListener {
            stopRingtone()
            finish()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbarAlarm)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.alarm_on)
    }

    private fun showGifAndPlayRingtone() {
        gifImageView.visibility = View.VISIBLE

        if (!ringtone.isPlaying) {
            ringtone.play()
        }
    }

    private fun stopRingtone() {
        if (ringtone.isPlaying) {
            ringtone.stop()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                stopRingtone()
                finishAffinity()
                Toast.makeText(this, "Программа завершена", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}