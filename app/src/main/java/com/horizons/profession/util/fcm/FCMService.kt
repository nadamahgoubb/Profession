package com.horizons.profession.util.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.horizons.profession.R
import com.horizons.profession.data.dataSource.repoistry.PrefsHelper
import com.horizons.profession.ui.activity.MainActivity
import com.horizons.profession.util.Constants.CHANNEL_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

import javax.inject.Inject

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMessagingServ"
        const val NOTIFICATION_ITEM = "NOTIFICATION_ITEM"
        //   const val REFRESH_CRYSTALS = "REFRESH_CRYSTALS"
    }


    @Inject
    lateinit var fcmUseCase: FcmUseCase
    override fun onNewToken(s: String) {
        super.onNewToken(s)
        PrefsHelper.setFCMToken(s)
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        fcmUseCase.sendFcmTokenToServer(params = FcmParam(s))
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        showNotification(remoteMessage.data, remoteMessage.notification)
        //  NotificationUtil(context = this).sendNotification(response)
        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("Notification", "c" + " ${it.body}+ ${it.title}")
        }

        //  var c=    remoteMessage.getTClass<FcmResponse>()  as FcmResponse
        //    showAppToast(c.title)
    }


    private fun showNotification(
        remoteMessage: Map<String, String>, notification: RemoteMessage.Notification?
    ) {
        Log.d(TAG, "showNotification: $remoteMessage")
        //  val soundUri =
        //       Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.notification)
        //     var pendingIntent : PendingIntent? = null
        val title: String = remoteMessage["title"].toString()
        val body: String = remoteMessage["body"].toString()
        var bundel = Bundle()
        val contentIntent: PendingIntent? = (if (remoteMessage.get("key") != null) {
            val notifactionId: Int = remoteMessage["key"].toString().toInt()
            val orderId: String = remoteMessage["order_id"].toString()

                sendRealTimeBroadcast()
                val intent = Intent(this, MainActivity::class.java)
                PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_IMMUTABLE)


        } else {
            //  sendRealTimeBroadcastNewOrder()
            sendRealTimeBroadcast()
            val intent = Intent(this, MainActivity::class.java)
            PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_IMMUTABLE)

        })


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }


        //   myRemoteViews.setOnClickPendingIntent(R.id.widget_button, myPendingIntent)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID).setAutoCancel(true)
            .setSmallIcon(R.drawable.logo).setContentTitle(title)
            .setContentText(body).setContentIntent(contentIntent)
            // .setSound(soundUri)
            .setVibrate(pattern)


        notificationManager.notify(Random().nextInt(), notification.build())
      }



/*  private fun showNotification(
      remoteMessage: Map<String, String>,
      notification: RemoteMessage.Notification?
  ) {
      Log.d(TAG, "showNotification: $remoteMessage")
      val intent = Intent(this, MainActivity::class.java)
     var contentIntent:  PendingIntent? =    PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_IMMUTABLE)

      val notificationManager =
          getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500)


      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          createNotificationChannel(notificationManager)
      }


      //   myRemoteViews.setOnClickPendingIntent(R.id.widget_button, myPendingIntent)

      val notification = NotificationCompat.Builder(this, CHANNEL_ID)
          .setAutoCancel(true)
          .setSmallIcon(R.drawable.logo)
          .setContentTitle(notification?.title)
          .setContentText(notification?.body)
          .setContentIntent(contentIntent)
          // .setSound(soundUri)
          .setVibrate(pattern)


      notificationManager.notify(Random().nextInt(), notification.build())
  }

*/
private fun sendRealTimeBroadcast() {
    val intent =
        Intent("MainActivity.MAIN_SCREEN_ACTION") //used to receive in intent filter when register the broadcast
    sendBroadcast(intent)
}


@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(notificationManager: NotificationManager) {
  //  val soundUri =
  //      Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.notification)
    val channelName = "Channel"
    val audioAttributes =
        AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM).build()
    val channel = NotificationChannel(
        CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "A notification from Profwssion  App"
        enableLights(true)
        lightColor = Color.GREEN
    }
    //channel.setSound(soundUri, audioAttributes)
    notificationManager.createNotificationChannel(channel)
}



}
