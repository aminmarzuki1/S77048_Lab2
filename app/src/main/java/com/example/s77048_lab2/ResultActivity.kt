package com.example.s77048_lab2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = intent.getStringExtra("EXTRA_NAME") ?: ""
        val dob = intent.getStringExtra("EXTRA_DOB") ?: ""

        val textNama = findViewById<TextView>(R.id.textNama)
        val textTarikh = findViewById<TextView>(R.id.textTarikh)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val nameWithoutSpaces = name.replace(" ", "")
        val totalCharacters = nameWithoutSpaces.length
        val vowelCount = name.count { it.lowercaseChar() in "aeiou" }

        val repeatedCharsList = nameWithoutSpaces
            .groupBy { it.lowercaseChar() }
            .filter { it.value.size > 1 }
            .keys
            .joinToString(", ")

        val repeatedCharacters = if (repeatedCharsList.isNotEmpty()) repeatedCharsList else "Tiada"

        textNama.text = "Nama: $name\n" +
                "Jumlah abjad: $totalCharacters\n" +
                "Bilangan vokal: $vowelCount\n" +
                "Abjad berulang: $repeatedCharacters"

        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatter.isLenient = false
            val birthDate = formatter.parse(dob)

            if (birthDate != null) {
                val dobCal = Calendar.getInstance().apply { time = birthDate }
                val todayCal = Calendar.getInstance()

                var age = todayCal.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR)
                if (todayCal.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
                    age--
                }

                val malayDays = mapOf(
                    Calendar.SUNDAY to "Ahad",
                    Calendar.MONDAY to "Isnin",
                    Calendar.TUESDAY to "Selasa",
                    Calendar.WEDNESDAY to "Rabu",
                    Calendar.THURSDAY to "Khamis",
                    Calendar.FRIDAY to "Jumaat",
                    Calendar.SATURDAY to "Sabtu"
                )
                val dayOfWeek = malayDays[dobCal.get(Calendar.DAY_OF_WEEK)] ?: "Khamis"

                textTarikh.text = "Umur anda: $age tahun\n" +
                        "Dilahirkan pada: $dayOfWeek"
            }
        } catch (e: Exception) {
            textTarikh.text = "Invalid date format (Use: YYYY-MM-DD)"
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}