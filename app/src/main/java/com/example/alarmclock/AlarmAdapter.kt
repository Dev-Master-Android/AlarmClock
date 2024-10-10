package com.example.alarmclock



import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlarmAdapter(
    private val alarms: List<Alarm>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTime: TextView = itemView.findViewById(R.id.textViewTime)
        val buttonDelete: Button = itemView.findViewById(R.id.btnDelete)

        @SuppressLint("DefaultLocale")
        fun bind(alarm: Alarm) {
            textViewTime.text = String.format("%02d:%02d", alarm.hour, alarm.minute)
            buttonDelete.setOnClickListener {
                onDeleteClick(alarm.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(alarms[position])
    }

    override fun getItemCount(): Int {
        return alarms.size
    }
}