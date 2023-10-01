package com.example.practical_8_mad_041_5a3

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var alarm_set: MaterialButton =findViewById(R.id.Alarm_set)
        var m2: MaterialCardView =findViewById(R.id.set_alarm2);
        var m1: MaterialCardView =findViewById(R.id.set_alarm);
        var c_time: TextView =findViewById(R.id.c_time);
        var set_time: TextView =findViewById(R.id.set_time);
        val alarm_show: MaterialCardView =findViewById(R.id.alarm_show);
        val set: Button =findViewById(R.id.set);
        val timepicker: TimePicker =findViewById(R.id.timePicker);
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        c_time.text=currentDateAndTime;
        alarm_set.setOnClickListener(){
            alarm_show.visibility= View.VISIBLE
            m1.visibility= View.INVISIBLE
        }
        var alarm_set2: MaterialButton =findViewById(R.id.Alarm_set2)
        alarm_set2.setOnClickListener(){
            stop_alarm();
            m2.visibility= View.INVISIBLE
        }
        set.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            if (Build.VERSION.SDK_INT >= 23) {
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timepicker.hour,
                    timepicker.minute,
                    0
                )
            }
            else {
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timepicker.hour,
                    timepicker.minute,
                    0
                )
            }
            set_alarm(calendar.timeInMillis,"start")
            m1.visibility= View.VISIBLE
            m2.visibility= View.VISIBLE
            alarm_show.visibility= View.GONE
            set_time.text=timepicker.hour.toString() + "/"+timepicker.minute.toString()
        }

    }
    fun set_alarm(militime:Long,action:String){
        val intent_alarm= Intent(applicationContext,AlarmBrodcastReceiver::class.java)
        intent_alarm.putExtra(AlarmBrodcastReceiver.ALARMKEY,action)
        val pendingintent=
            PendingIntent.getBroadcast(applicationContext,2345,intent_alarm, PendingIntent.FLAG_UPDATE_CURRENT)
        val manager=getSystemService(ALARM_SERVICE) as AlarmManager
        if(action==AlarmBrodcastReceiver.ALARM_START){
            manager.setExact(AlarmManager.RTC_WAKEUP,militime,pendingintent)
        }
    }
    fun stop_alarm(){
        var intent= Intent(applicationContext,AlarmService::class.java);
        stopService(intent);
    }

}