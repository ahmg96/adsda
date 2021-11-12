package com.example.wi_projekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wi_projekt.fragments.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    lateinit var userId: String
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        userId = intent.getStringExtra("user_id").toString()
        database = FirebaseDatabase.getInstance().getReference("user")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val navController = findNavController(R.id.fr_wrapper)
        bottom_navigation.setupWithNavController(navController)
    }
}