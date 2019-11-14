package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etNome : EditText
    private lateinit var nome:String
    private lateinit var btOk : Button
    private lateinit var  mensagem : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.etNome = findViewById(R.id.etNome)
        this.btOk = findViewById(R.id.btOk)

        val msg = gerarMensagem()


        btOk.setOnClickListener(){

            nome = etNome.text.toString()
            mensagem = "$msg  $nome!"


            issueNotification()
        }

    }


    fun gerarHora(): Int {
        var calendario = Calendar.getInstance()
        var resultCalendar = calendario.get(Calendar.HOUR_OF_DAY)

        return resultCalendar
    }


    fun gerarMensagem(): String {

        var hora = gerarHora()

        if (hora < 6) {
            return "Boa madrugada,"
        } else if (hora > 6 && hora < 12) {
            return "Bom dia,"
        } else if (hora > 12 && hora < 18) {
            return "Boa tarde,"
        } else {
            return "Boa noite,"
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun makeNotificationChannel(id: String, name: String, importance: Int) {
        val channel = NotificationChannel(id, name, importance)
        channel.setShowBadge(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }


    fun issueNotification() {

        val intent = Intent(this, ExibirActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        intent.putExtra("MSG_IDA", mensagem)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("CHANNEL_1", "Example channel", NotificationManager.IMPORTANCE_DEFAULT)
        }

        val notification = NotificationCompat.Builder(this, "CHANNEL_1")

        notification
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Olá")
            .setContentText(mensagem)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1, notification.build())
    }


}

