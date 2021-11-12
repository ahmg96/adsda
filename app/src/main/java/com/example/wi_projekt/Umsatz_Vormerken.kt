package com.example.wi_projekt
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class Umsatz_Vormerken : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_umsatz_vormerken)

        //Kalendar
        val k = Calendar.getInstance()
        val year = k.get(Calendar.YEAR)
        val month = k.get(Calendar.MONTH)
        val day = k.get(Calendar.DAY_OF_MONTH)
        val safe = findViewById<Button>(R.id.pickDateSafe)
        val name = findViewById<EditText>(R.id.neuer_umsatz_name)
        val preis = findViewById<EditText>(R.id.neuer_umsatz_preis)
        val datum = findViewById<TextView>(R.id.neuer_umsatz_datum)

        //On click Listener für den Dialog

        datum.setOnClickListener(){
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mDay, mMonth, mYear ->
                datum.setText(""+mDay + "/" + mMonth +"/" + mYear) }, day, month, year)
            dpd.show()
        }

        safe.setOnClickListener {
            if(name.text.toString().isEmpty() || preis.text.toString().isEmpty() || datum.text == "Datum"){
                showNoticeDialog()
                return@setOnClickListener
            }else{
            }
        }
    }

    fun showNoticeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("EINGABE NICHT KORREKT")
        builder.setMessage("Dieses Feld darf nicht leer abgesendet werden!")
        builder.setNegativeButton("Verstanden") { dialogInterface: DialogInterface, i: Int -> }
        builder.show()
    }
}