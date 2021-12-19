package uz.pdp.todoapp.nitification

import android.content.Context
import android.content.ContextWrapper
import android.R

import android.app.NotificationManager

import android.app.NotificationChannel

import android.os.Build

import android.annotation.TargetApi
import android.app.Notification
import androidx.annotation.RequiresApi


class NotificationHelper(base: Context?) : ContextWrapper(base) {

    val channelID = "channelID"
    val channelName = "Channel Name"
    private var mManager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        getManager()!!.createNotificationChannel(channel)
    }

    fun getManager(): NotificationManager? {
        if (mManager == null) {
            mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }
        return mManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getChannelNotification(): Notification.Builder {
        return Notification.Builder(applicationContext, channelID)
            .setContentTitle("Alarm!")
            .setContentText("Your AlarmManager is working.")
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
    }

}