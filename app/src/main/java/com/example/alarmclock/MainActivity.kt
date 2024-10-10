package com.example.alarmclock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnSetAlarm: Button

    private val alarms = mutableListOf<Alarm>()
    private lateinit var alarmAdapter: AlarmAdapter

    @SuppressLint("DefaultLocale", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        btnSetAlarm = findViewById(R.id.btnSetAlarm)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        alarmAdapter = AlarmAdapter(alarms) { alarmId ->
            removeAlarm(alarmId)
        }
        recyclerView.adapter = alarmAdapter
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Будильник"
        btnSetAlarm.setOnClickListener { showTimePicker() }
    }

    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Выберите время будильника")
            .build()

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute

            val alarmId = alarms.size + 1

            alarms.add(Alarm(alarmId, hour, minute))
            alarmAdapter.notifyItemInserted(alarms.size - 1)

            setAlarm(hour, minute, alarmId)

            Toast.makeText(this, getString(R.string.alarm_add), Toast.LENGTH_SHORT).show()
        }

        picker.show(supportFragmentManager, "MaterialTimePicker")
    }

    private fun setAlarm(hour: Int, minute: Int, alarmId: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this,
            alarmId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeAlarm(alarmId: Int) {
        alarms.removeIf { it.id == alarmId }
        alarmAdapter.notifyDataSetChanged()

        Toast.makeText(this, getString(R.string.alarm_delete), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity()
                Toast.makeText(this, "Программа завершена", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}