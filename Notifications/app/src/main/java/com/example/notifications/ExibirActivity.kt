package com.example.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ExibirActivity : AppCompatActivity() {

    lateinit var tvMensagem: TextView
    lateinit var msg:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exibir)

        this.tvMensagem = findViewById(R.id.tvMensagem)

        msg = getIntent().getStringExtra("MSG_IDA")
        tvMensagem.setText(msg)
    }
}
