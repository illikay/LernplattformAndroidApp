package com.kayikci.lernplattform2.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kayikci.lernplattform2.databinding.ActivityQuestionDetailBinding
import com.kayikci.lernplattform2.models.Question
import com.kayikci.lernplattform2.services.QuestionService
import com.kayikci.lernplattform2.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.ZonedDateTime
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

        lifecycleScope.launch(Dispatchers.IO) {

            val questionService = ServiceBuilder.buildService(QuestionService::class.java)


            try {
                val response = questionService.getQuestion(examId, questionId)
                if (response.isSuccessful) {
                    val question = response.body()
                    withContext(Dispatchers.Main) {

                        question?.let {
                            activityQuestionDetailBinding.etFragestellung.setText(question.questionFrage)
                            activityQuestionDetailBinding.etHinweis.setText(question.questionHinweis)
                            activityQuestionDetailBinding.etLoesung.setText(question.questionLoesung)

                            activityQuestionDetailBinding.questionCollapsingToolbar.title =
                                question.questionFrage
                        }
                    }


                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@QuestionDetailActivity,
                            "Failed to retrieve details",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@QuestionDetailActivity,
                        "Failed to retrieve details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }
    }

    @SuppressLint("WeekBasedYear")
    private fun initUpdateButton(questionId: Long, examId: Long) {

        activityQuestionDetailBinding.btnUpdate.setOnClickListener {
            val newQuestion = Question()

            newQuestion.questionFrage =
                activityQuestionDetailBinding.etFragestellung.text.toString()
            newQuestion.questionHinweis = activityQuestionDetailBinding.etHinweis.text.toString()
            newQuestion.questionLoesung = activityQuestionDetailBinding.etLoesung.text.toString()


            newQuestion.erstellDatum = ZonedDateTime.now(ZoneOffset.UTC)
            newQuestion.aenderungsDatum = ZonedDateTime.now(ZoneOffset.UTC)
            newQuestion.isBeantwortet = true

            lifecycleScope.launch(Dispatchers.IO) {

                val questionService = ServiceBuilder.buildService(QuestionService::class.java)

                try {
                    val response = questionService.updateQuestion(examId, questionId, newQuestion)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            finish() // Move back to DestinationListActivity
                            Toast.makeText(
                                this@QuestionDetailActivity,
                                "Item Updated Successfully", Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@QuestionDetailActivity,
                                "Failed to update item", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@QuestionDetailActivity,
                            "Failed to update item", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }

        }
    }

    private fun initDeleteButton(questionId: Long, examId: Long) {

        activityQuestionDetailBinding.btnDelete.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                val questionService = ServiceBuilder.buildService(QuestionService::class.java)


                try {
                    val response = questionService.deleteQuestion(examId, questionId)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            finish() // Move back to DestinationListActivity
                            Toast.makeText(
                                this@QuestionDetailActivity,
                                "Successfully Deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@QuestionDetailActivity,
                                "Failed to Delete",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@QuestionDetailActivity,
                            "Failed to Delete",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
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
