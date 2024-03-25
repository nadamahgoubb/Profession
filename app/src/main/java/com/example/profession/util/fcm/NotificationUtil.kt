package com.example.profession.util.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
 import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.profession.R
import com.example.profession.ui.activity.MainActivity
import com.example.profession.util.Constants.CHANNEL_ID
import com.google.firebase.messaging.RemoteMessage
  import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Mahmoud Ayman on 07/08/2022.
 * Email: mahmoud_aymann@outlook.com.
 */
class NotificationUtil @Inject constructor(@ApplicationContext val context: Context) {
    companion object

    fun sendNow(title: String?, body: String?) {
        val notificationBuilder = getNotificationBuilder(null)
            .setContentTitle(title)
            .setContentText(body)
        notify(notificationBuilder)
    }

    private fun sendNow(body: String, response: NotificationItem) {
        val notificationBuilder = getNotificationBuilder(response)
            .setContentText(body)
        notify(notificationBuilder)
    }

    private fun getNotificationBuilder(response: NotificationItem?): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.logo)
                .setAutoCancel(true).priority = NotificationCompat.PRIORITY_DEFAULT
        }
        val intent = Intent()
        intent.setClass(context, MainActivity::class.java)
        intent.putExtra(FCMService.NOTIFICATION_ITEM, response)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        setPendingIntent(intent, builder)
        return builder
    }

    private fun setPendingIntent(intent: Intent, builder: NotificationCompat.Builder) {
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),  //*Request code*/,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        builder.setContentIntent(pendingIntent)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "name", importance)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

  /*  private fun createNotificationChannel( ) {

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val serviceChannel = NotificationChannel(
            Constants.CHANNEL_ID, Constants.CHANNEL_Name,
           importance
        )
        manager!!.createNotificationChannel(serviceChannel)

    }
 */


    private fun notify(notificationBuilder: NotificationCompat.Builder) {
        // Since android Oreo notification channel is needed.
        createNotificationChannel()
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, notificationBuilder.build())
        }
    }

}