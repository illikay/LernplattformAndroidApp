package com.kayikci.lernplattform2.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.kayikci.lernplattform2.databinding.ActivityExamCreateBinding
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question

import com.kayikci.lernplattform2.services.ExamService
import com.kayikci.lernplattform2.services.ServiceBuilder



import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExamCreateActivity : AppCompatActivity() {
    private lateinit var B: ActivityExamCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = ActivityExamCreateBinding.inflate(layoutInflater)
        setContentView(B.root)

        setSupportActionBar(B.toolbar)
        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        B.btnAdd.setOnClickListener {
            val newExam = Exam()
            newExam.pruefungsName = B.etName.text.toString()
            newExam.info = B.etInfo.text.toString()
            newExam.beschreibung = B.etBeschreibung.text.toString()

            val format = SimpleDateFormat("dd.MM.YYYY HH:mm:ss")
            val dateString: String = format.format(Date())
            newExam.erstellDatum = dateString
            newExam.aenderungsDatum = dateString
            newExam.anzahlFragen = 5



            val examService = ServiceBuilder.buildService(ExamService::class.java)
            val requestCall = examService.addExam(newExam)

            requestCall.enqueue(object: Callback<Exam> {

                override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var newlyCreatedExam = response.body() // Use it or ignore it
                        Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Exam>, t: Throwable) {
                    Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
