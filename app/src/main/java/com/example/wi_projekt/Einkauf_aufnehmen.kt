package com.example.wi_projekt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wi_projekt.databinding.ActivityEinkaufAufnehmenBinding


class Einkauf_aufnehmen : AppCompatActivity() {
    lateinit var binding: ActivityEinkaufAufnehmenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEinkaufAufnehmenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn = binding.umsatzVormerkenBtn

        btn.setOnClickListener {
            val intent = Intent(this, Umsatz_Vormerken::class.java)
            startActivity(intent)
        }
    }
}

