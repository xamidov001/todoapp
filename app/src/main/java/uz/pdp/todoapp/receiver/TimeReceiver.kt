package uz.pdp.todoapp.receiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import uz.pdp.todoapp.nitification.NotificationHelper
import uz.pdp.todoapp.R
import uz.pdp.todoapp.room.database.DatabaseHelper

class TimeReceiver : BroadcastReceiver() {

    private lateinit var databaseTime: DatabaseHelper
    private lateinit var listMusic: ArrayList<Int>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        databaseTime = DatabaseHelper.getInstance(context)
        loading()

        val notificationHelper = NotificationHelper(context)
        val nb: Notification.Builder = notificationHelper.getChannelNotification()
        notificationHelper.getManager()!!.notify(1, nb.build())
        val mediaPlayer = MediaPlayer.create(context, listMusic[2])
        mediaPlayer.start()
    }

    private fun loading() {
        listMusic = ArrayList()
        listMusic.add(R.raw.alarm_mobile_tone)
        listMusic.add(R.raw.breeze_music_miui)
        listMusic.add(R.raw.mi_alarm_tone_day_dream)
        listMusic.add(R.raw.morning_alarm)
        listMusic.add(R.raw.walton_mobile_tone)
    }
}