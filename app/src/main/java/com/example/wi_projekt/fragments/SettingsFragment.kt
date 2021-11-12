package com.example.wi_projekt.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.preference.*
import com.example.wi_projekt.Einstellungen
import com.example.wi_projekt.Login
import com.example.wi_projekt.MainActivity2
import com.example.wi_projekt.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import android.content.Intent as Intent

class SettingsFragment() : PreferenceFragmentCompat(){

    //Preferenzen
    private var prefCurrency:ListPreference? = null
    private var prefLoggedIn:SwitchPreference? = null
    private var prefLogout:Preference? = null

    //Values
    private lateinit var sharedPreference: SharedPreferences
    private var currency = ""
    private var login = false
    private var logout = false

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        prefCurrency = findPreference("pre_cur")
        prefLoggedIn = findPreference("prefEingeloggt")
        prefLogout = findPreference("prefAusloggen")

        loadDataFromPreference()

        prefCurrency?.summaryProvider =  ListPreference.SimpleSummaryProvider.getInstance()

        // Add onclicklistener fürs ausloggen
        prefLogout?.setOnPreferenceClickListener {


            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, Login::class.java)
            startActivity(intent)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("logout", true)
            true
        }

    }

    private fun loadDataFromPreference(){
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(requireContext())
        currency = sharedPreference.getString("pre_cur", "")!!
        login = sharedPreference.getBoolean("prefEingeloggt", false)
        logout = sharedPreference.getBoolean("prefAusloggen", false)

        // prefCurrency?.summary = currency -> Gibt die Value der Pref an -> z.B EUR für European Euro
    }
}
