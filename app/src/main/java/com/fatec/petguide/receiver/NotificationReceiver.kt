package com.fatec.petguide.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.fatec.petguide.R
import com.fatec.petguide.data.entity.ReminderEntity
import com.fatec.petguide.data.repository.AccountRepository
import com.fatec.petguide.data.repository.ReminderRepository
import com.fatec.petguide.data.util.Constants
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.getValue
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Locale


class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val channelId = "running_channel"

        if (getReminderForToday()) {
            context?.let { ctx ->
                val notificationManager =
                    ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val builder = NotificationCompat.Builder(ctx, channelId)
                    .setSmallIcon(R.drawable.app_logo)
                    .setContentTitle("Verifique a aplicação.")
                    .setContentText("Você tem lembretes marcados para hoje.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                notificationManager.notify(1, builder.build())
            }
        }
    }

    private fun getReminderForToday(): Boolean {
        val list = ArrayList<String>()

        ReminderRepository(AccountRepository().getCurrentUserId()).getReminderList(object :
            ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.exists()) {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(Calendar.getInstance().time)
                    if (formattedDate == snapshot.child(Constants.REMINDER_DATE).getValue<String>()) {
                        list.add((snapshot.child(Constants.REMINDER_TITLE).getValue<String>().toString()))
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })

        return list.isNotEmpty()
    }

}