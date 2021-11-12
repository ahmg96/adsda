package com.example.wi_projekt


import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wi_projekt.databinding.ActivityMonatsbudgetBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_monatsbudget.*
import kotlin.properties.Delegates


class Monatsbudget : AppCompatActivity() {
    lateinit var binding: ActivityMonatsbudgetBinding
    lateinit var userId: String
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        userId = intent.getStringExtra("user_id").toString()
        database = FirebaseDatabase.getInstance().getReference("user")
        super.onCreate(savedInstanceState)
        binding = ActivityMonatsbudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.submit.text = getData("budget").toString()

        //updateListener
        val budgetListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get budget value and use the value to update the UI
                val budget = dataSnapshot.child(userId).child("budget").getValue()
                // val budget = dataSnapshot.get() -> Would return all Users with data in a Hashmap or JSON
                binding.submit.text = budget.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(budgetListener)

        // end

        binding.submit.setOnClickListener {
            val monatsbudget = inp_monatsbudget.text.toString().toDouble()
            updateUser(monatsbudget)
        }
    }

    fun updateUser(data: Double) {
        database = FirebaseDatabase.getInstance().getReference("user")
        val update = mapOf<String, Any>(
            "budget" to data
        )
        database.child(userId as String).updateChildren(update).addOnSuccessListener {
            Toast.makeText(
                this,
                userId, // Gibt den Grund des Fehlens aus, maybe noch auf simple Deutsche meldung abändern und den Fehler loggen in Firebase
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
