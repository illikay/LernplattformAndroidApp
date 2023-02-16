package com.kayikci.lernplattform2.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.kayikci.lernplattform2.databinding.ActivityQuestionDetailBinding
import com.kayikci.lernplattform2.models.Question
import com.kayikci.lernplattform2.services.QuestionService
import com.kayikci.lernplattform2.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class QuestionDetailActivity : AppCompatActivity() {

    private lateinit var activityQuestionDetailBinding: ActivityQuestionDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityQuestionDetailBinding = ActivityQuestionDetailBinding.inflate(layoutInflater)
        setContentView(activityQuestionDetailBinding.root)

        setSupportActionBar(activityQuestionDetailBinding.questionDetailToolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val questionId = intent.getLongExtra("questionId", 0)

        val examId = intent.getLongExtra("examId", 0)


        loadDetails(questionId, examId)

        initUpdateButton(questionId, examId)

        initDeleteButton(questionId, examId)


    }

    private fun loadDetails(questionId: Long, examId: Long) {

        val questionService = ServiceBuilder.buildService(QuestionService::class.java)
        val requestCall = questionService.getQuestion(examId, questionId)

        requestCall.enqueue(object : Callback<Question> {

            override fun onResponse(call: Call<Question>, response: Response<Question>) {

                if (response.isSuccessful) {
                    val question = response.body()
                    question?.let {
                        activityQuestionDetailBinding.etFragestellung.setText(question.questionFrage)
                        activityQuestionDetailBinding.etHinweis.setText(question.questionHinweis)
                        activityQuestionDetailBinding.etLoesung.setText(question.questionLoesung)

                        activityQuestionDetailBinding.questionCollapsingToolbar.title =
                            question.questionFrage
                    }
                } else {
                    Toast.makeText(
                        this@QuestionDetailActivity,
                        "Failed to retrieve details",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<Question>, t: Throwable) {
                Toast.makeText(
                    this@QuestionDetailActivity,
                    "Failed to retrieve details $t",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    @SuppressLint("WeekBasedYear")
    private fun initUpdateButton(questionId: Long, examId: Long) {

        activityQuestionDetailBinding.btnUpdate.setOnClickListener {
            val newQuestion = Question()

            newQuestion.questionFrage =
                activityQuestionDetailBinding.etFragestellung.text.toString()
            newQuestion.questionHinweis = activityQuestionDetailBinding.etHinweis.text.toString()
            newQuestion.questionLoesung = activityQuestionDetailBinding.etLoesung.text.toString()

            val format = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY)
            val dateString: String = format.format(Date())
            newQuestion.erstellDatum = dateString
            newQuestion.aenderungsDatum = dateString
            newQuestion.isBeantwortet = true

            val questionService = ServiceBuilder.buildService(QuestionService::class.java)
            val requestCall = questionService.updateQuestion(examId, questionId, newQuestion)

            requestCall.enqueue(object : Callback<Question> {

                override fun onResponse(call: Call<Question>, response: Response<Question>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(
                            this@QuestionDetailActivity,
                            "Item Updated Successfully", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@QuestionDetailActivity,
                            "Failed to update item", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Question>, t: Throwable) {
                    Toast.makeText(
                        this@QuestionDetailActivity,
                        "Failed to update item", Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun initDeleteButton(questionId: Long, examId: Long) {

        activityQuestionDetailBinding.btnDelete.setOnClickListener {

            val questionService = ServiceBuilder.buildService(QuestionService::class.java)
            val requestCall = questionService.deleteQuestion(examId, questionId)

            requestCall.enqueue(object : Callback<Unit> {

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(
                            this@QuestionDetailActivity,
                            "Successfully Deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@QuestionDetailActivity,
                            "Failed to Delete",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(
                        this@QuestionDetailActivity,
                        "Failed to Delete",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, ExamDetailActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
