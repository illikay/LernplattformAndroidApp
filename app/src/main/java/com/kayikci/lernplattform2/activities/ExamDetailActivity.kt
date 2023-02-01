package com.kayikci.lernplattform2.activities


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.kayikci.lernplattform2.databinding.ActivityExamDetailBinding
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


class ExamDetailActivity : AppCompatActivity() {

    private lateinit var B: ActivityExamDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = ActivityExamDetailBinding.inflate(layoutInflater)
        setContentView(B.root)

        setSupportActionBar(B.detailToolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        var globalExamId = 0

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            globalExamId = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(globalExamId)

            initUpdateButton(globalExamId)

            initDeleteButton(globalExamId)
        }



        B.questionfab.setOnClickListener {

            val intent = Intent(this@ExamDetailActivity, QuestionCreateActivity::class.java)

            intent.putExtra("actualExam", globalExam)
            intent.putExtra("StringId", globalExamId)
            startActivity(intent)
        }

    }

    private fun loadDetails(examId: Int) {

        val examService = ServiceBuilder.buildService(ExamService::class.java)
        val requestCall = examService.getExam(examId)

        requestCall.enqueue(object : retrofit2.Callback<Exam> {

            override fun onResponse(call: Call<Exam>, response: Response<Exam>) {

                if (response.isSuccessful) {
                    val exam = response.body()
                    exam?.let {
                        globalExam = exam
                        B.etName.setText(exam.pruefungsName)
                        B.etInfo.setText(exam.info)
                        B.etBeschreibung.setText(exam.beschreibung)

                        B.collapsingToolbar.title = exam.pruefungsName
                    }
                } else {
                    Toast.makeText(this@ExamDetailActivity, "Failed to retrieve details", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Exam>, t: Throwable) {
                Toast.makeText(
                    this@ExamDetailActivity,
                    "Failed to retrieve details " + t.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initUpdateButton(id: Int) {

        B.btnUpdate.setOnClickListener {
            var newExam = Exam()

            newExam.pruefungsName = B.etName.text.toString()
            newExam.info = B.etInfo.text.toString()
            newExam.beschreibung = B.etBeschreibung.text.toString()

            val format = SimpleDateFormat("dd.MM.YYYY HH:mm:ss")
            val dateString: String = format.format(Date())
            newExam.erstellDatum = dateString
            newExam.aenderungsDatum = dateString
            newExam.anzahlFragen = 3





            val examService = ServiceBuilder.buildService(ExamService::class.java)
            val requestCall = examService.updateExam(id, newExam)



            requestCall.enqueue(object: Callback<Exam> {

                override fun onResponse(call: Call<Exam>, response: Response<Exam>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var updatedDestination = response.body() // Use it or ignore It
                        Toast.makeText(this@ExamDetailActivity,
                            "Item Updated Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ExamDetailActivity,
                            "Failed to update item", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Exam>, t: Throwable) {
                    Toast.makeText(this@ExamDetailActivity,
                        "Failed to update item", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initDeleteButton(examId: Int) {

        B.btnDelete.setOnClickListener {

            val examService = ServiceBuilder.buildService(ExamService::class.java)
            val requestCall = examService.deleteExam(examId)

            requestCall.enqueue(object: Callback<Unit> {

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(this@ExamDetailActivity, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ExamDetailActivity, "Failed to Delete", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(this@ExamDetailActivity, "Failed to Delete", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, ExamListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
        var globalExam:Exam = Exam()
    }
}
