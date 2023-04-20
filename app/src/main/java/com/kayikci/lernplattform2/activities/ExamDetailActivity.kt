package com.kayikci.lernplattform2.activities


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kayikci.lernplattform2.databinding.ActivityExamDetailBinding
import com.kayikci.lernplattform2.helpers.QuestionAdapter
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.services.ExamService
import com.kayikci.lernplattform2.services.QuestionService
import com.kayikci.lernplattform2.services.ServiceBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ExamDetailActivity : AppCompatActivity() {

    private lateinit var activityExamDetailBinding: ActivityExamDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityExamDetailBinding = ActivityExamDetailBinding.inflate(layoutInflater)
        setContentView(activityExamDetailBinding.root)

        setSupportActionBar(activityExamDetailBinding.detailToolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: Long = intent.getLongExtra("examId", 0)


        val actualExam: Exam?

        if (Build.VERSION.SDK_INT >= 33) {
            actualExam = intent.getParcelableExtra("examObject", Exam::class.java)
        } else {
            @Suppress("DEPRECATION") actualExam = intent.getParcelableExtra("examObject")
        }

        //Getting Questions from API

        // getting the recyclerview by its id
        val recyclerview = activityExamDetailBinding.questionRecyclerView

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)



        loadDetails(id)

        loadQuestions(id)

        initUpdateButton(id)

        initDeleteButton(id)

        activityExamDetailBinding.questionfab.setOnClickListener {

            val intent = Intent(this@ExamDetailActivity, QuestionCreateActivity::class.java)
            intent.putExtra("examId", id)
            intent.putExtra("examObject", actualExam)


            startActivity(intent)
        }

    }

    private fun loadDetails(id: Long) {

        lifecycleScope.launch {

            val examService = ServiceBuilder.buildService(ExamService::class.java)


            try {
                val response = examService.getExam(id)
                if (response.isSuccessful) {
                    val exam = response.body()
                    exam?.let {
                        activityExamDetailBinding.etName.setText(exam.pruefungsName)
                        activityExamDetailBinding.etInfo.setText(exam.info)
                        activityExamDetailBinding.etBeschreibung.setText(exam.beschreibung)

                        activityExamDetailBinding.collapsingToolbar.title = exam.pruefungsName
                    }

                } else {
                    Toast.makeText(
                        this@ExamDetailActivity,
                        "Failed to retrieve details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ExamDetailActivity,
                    "Failed to retrieve details",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    @SuppressLint("WeekBasedYear")
    private fun initUpdateButton(id: Long) {

        activityExamDetailBinding.btnUpdate.setOnClickListener {

            val newExam = Exam()

            newExam.pruefungsName = activityExamDetailBinding.etName.text.toString()
            newExam.info = activityExamDetailBinding.etInfo.text.toString()
            newExam.beschreibung = activityExamDetailBinding.etBeschreibung.text.toString()

            val format = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY)
            val dateString: String = format.format(Date())
            newExam.erstellDatum = dateString
            newExam.aenderungsDatum = dateString
            newExam.anzahlFragen = 3

            lifecycleScope.launch {

                val examService = ServiceBuilder.buildService(ExamService::class.java)


                try {
                    val response = examService.updateExam(id, newExam)
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(
                            this@ExamDetailActivity,
                            "Item Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@ExamDetailActivity, "Failed to update item", Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@ExamDetailActivity, "Failed to update item", Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }

    private fun initDeleteButton(id: Long) {

        activityExamDetailBinding.btnDelete.setOnClickListener {

            lifecycleScope.launch {
                val examService = ServiceBuilder.buildService(ExamService::class.java)


                try {
                    val response = examService.deleteExam(id)
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(
                            this@ExamDetailActivity, "Successfully Deleted", Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(
                            this@ExamDetailActivity, "Failed to Delete", Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@ExamDetailActivity, "Failed to Delete", Toast.LENGTH_SHORT
                    ).show()
                }

            }

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

    override fun onResume() {
        super.onResume()

        val id: Long = intent.getLongExtra("examId", 0)
        //loadQuestions(id)

        loadQuestions(id)
    }

    private fun loadQuestions(id: Long) {

        lifecycleScope.launch {

            val questionService = ServiceBuilder.buildService(QuestionService::class.java)

            try {
                val response = questionService.getQuestionList(id)
                if (response.isSuccessful) {
                    val questionList = response.body()!!
                    val adapter = QuestionAdapter(questionList, id)

                    activityExamDetailBinding.questionRecyclerView.adapter = adapter

                } else if (response.code() == 401) {
                    Toast.makeText(
                        this@ExamDetailActivity,
                        "Your session has expired. Please Login again.",
                        Toast.LENGTH_LONG
                    ).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(
                        this@ExamDetailActivity, "Failed to retrieve items", Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ExamDetailActivity, "Error Occurred $e", Toast.LENGTH_LONG)
                    .show()
            }

        }

    }


}

