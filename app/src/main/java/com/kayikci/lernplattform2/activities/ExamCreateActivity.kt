package com.kayikci.lernplattform2.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kayikci.lernplattform2.databinding.ActivityExamCreateBinding
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.services.ExamService
import com.kayikci.lernplattform2.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ExamCreateActivity : AppCompatActivity() {

    private lateinit var activityExamCreateBinding: ActivityExamCreateBinding

    @SuppressLint("WeekBasedYear")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityExamCreateBinding = ActivityExamCreateBinding.inflate(layoutInflater)
        setContentView(activityExamCreateBinding.root)

        setSupportActionBar(activityExamCreateBinding.toolbar)
        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        activityExamCreateBinding.btnAdd.setOnClickListener {

            val newExam = Exam()
            newExam.pruefungsName = activityExamCreateBinding.etName.text.toString()
            newExam.info = activityExamCreateBinding.etInfo.text.toString()
            newExam.beschreibung = activityExamCreateBinding.etBeschreibung.text.toString()

            val format = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY)
            val dateString: String = format.format(Date())
            newExam.erstellDatum = dateString
            newExam.aenderungsDatum = dateString
            newExam.anzahlFragen = 5

            lifecycleScope.launch(Dispatchers.IO) {
                val examService = ServiceBuilder.buildService(ExamService::class.java)

                try {
                    val response = examService.addExam(newExam)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            finish() // Move back to DestinationListActivity
                            Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            finish() // Move back to DestinationListActivity
                            Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                        }

                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }

                }


            }

        }
    }
}
