package com.example.wi_projekt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.preference.PreferenceManager
import com.example.wi_projekt.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.Calendar


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        del_Data()
        if(load_Data()[1] != null){
            login_user(load_Data()[0].toString(), load_Data()[1].toString())
        }

        binding.tvReg.setOnClickListener {
            val intent = Intent(this@Login, Registrierung::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            //Checken ob die Eingabefelder leer sind
            when {
                TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@Login,
                        "Bitte gebe eine Email Adresse ein!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.etLoginPasswort.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@Login,
                        "Bitte gebe eine Passwort ein!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email = binding.etLoginEmail.text.toString().trim { it <= ' ' }
                    val pw = binding.etLoginPasswort.text.toString().trim { it <= ' ' }
                    login_user(email, pw)
                }
            }
        }
    }

    fun login_user(email : String, pw : String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    saveData(email, pw)
                    Toast.makeText(
                        this@Login,
                        "Erfolgreich Angemeldet",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Nutzer wird registriert und eingeloggt
                    val intent = Intent(this@Login, MainActivity2::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // cleart überflüssige Activitys im Background

                    //Nutzer daten werden aus der Datenbank geladen
                    //val database = FirebaseDatabase.getInstance().getReference("user")
                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                    startActivity(intent)
                    updateUser()
                    finish()
                } else {
                    // Fehlerhafte Eingabe abfangen,
                    Toast.makeText(
                        this@Login,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun updateUser() {
        val data = Calendar.getInstance().time
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val database = FirebaseDatabase.getInstance().getReference("user")
        val update = mapOf<String, Any>(
            "lLT" to data.toString()
        )
        database.child(userId).updateChildren(update).addOnSuccessListener {
            Toast.makeText(
                this,
                userId, // Gibt den Grund des Fehlens aus, maybe noch auf simple Deutsche meldung abändern und den Fehler loggen in Firebase
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun saveData(name : String, pw : String){
        val user_name: String = name
        val user_pw : String = pw

        val name_save = getSharedPreferences("username", Context.MODE_PRIVATE)
        val user_editor = name_save.edit()
        user_editor.apply{
            putString("username", user_name)
        }.apply()

        val pw_save = getSharedPreferences("userpw", Context.MODE_PRIVATE)
        val pw_editor = pw_save.edit()
        pw_editor.apply{
            putString("password", user_pw)
        }.apply()
    }

    private fun del_Data(){
        val logout = getSharedPreferences("prefAusloggen", MODE_PRIVATE).toString().toBoolean()
        if (logout) {
            val name_save = getSharedPreferences("username", Context.MODE_PRIVATE)
            val user_editor = name_save.edit()
            user_editor.apply {
                putString("username", null)
            }.apply()

            val pw_save = getSharedPreferences("userpw", Context.MODE_PRIVATE)
            val pw_editor = pw_save.edit()
            pw_editor.apply {
                putString("password", null)
            }.apply()
        }
    }

    private fun load_Data(): HashMap<Int, String?> {
        val name_save = getSharedPreferences("username", Context.MODE_PRIVATE)
        val pw_save = getSharedPreferences("userpw", Context.MODE_PRIVATE)
        val saved_username = name_save.getString("username", null)
        val saved_pw = pw_save.getString("password", null)
        return hashMapOf(
            0 to saved_username,
            1 to saved_pw
        )
    }
}