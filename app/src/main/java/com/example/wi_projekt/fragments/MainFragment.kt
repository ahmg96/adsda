package com.example.wi_projekt.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wi_projekt.R
import com.example.wi_projekt.databinding.ActivityMonatsbudgetBinding
import com.example.wi_projekt.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_monatsbudget.*

class MainFragment : Fragment() {

    lateinit var database: DatabaseReference
    lateinit var userId: String
    //lateinit var

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("user")
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        val budgetListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get budget value and use the value to update the UI
                val budget = dataSnapshot.child(userId).child("budget").getValue()
                // val budget = dataSnapshot.get() -> Would return all Users with data in a Hashmap or JSON
                submit?.text = budget.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(budgetListener)

        // end

        submit.setOnClickListener {
            val monatsbudget = inp_monatsbudget.text.toString().toDouble()
            updateUser(monatsbudget)
        }
    }

    fun updateUser(data: Double) {
        database = FirebaseDatabase.getInstance().getReference("user")
        val update = mapOf<String, Any>(
            "budget" to data
        )
        database.child(userId as String).updateChildren(update)
    }
}