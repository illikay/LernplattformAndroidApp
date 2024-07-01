package com.kayikci.lernplattform2.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kayikci.lernplattform2.databinding.ActivityQuestionCreateBinding
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question
import com.kayikci.lernplattform2.services.QuestionService
import com.kayikci.lernplattform2.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime


class QuestionCreateActivity : AppCompatActivity() {
    private lateinit var activityQuestionCreateBinding: ActivityQuestionCreateBinding
    var examId:Long = 0
    var actualExam:Exam? = null;

    @SuppressLint("WeekBasedYear")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityQuestionCreateBinding = ActivityQuestionCreateBinding.inflate(layoutInflater)
        setContentView(activityQuestionCreateBinding.root)

        setSupportActionBar(activityQuestionCreateBinding.detailToolbar)

        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        examId = intent.getLongExtra("examId", 0)




        if (Build.VERSION.SDK_INT >= 33) {
            actualExam = intent.getParcelableExtra("examObject", Exam::class.java)
        } else {
            @Suppress("DEPRECATION")
            actualExam = intent.getParcelableExtra("examObject")
        }



        activityQuestionCreateBinding.btnQuestionAdd.setOnClickListener {
            val newQuestion = Question()
            newQuestion.questionFrage =
                activityQuestionCreateBinding.etFragestellung.text.toString()
            newQuestion.questionHinweis = activityQuestionCreateBinding.etHinweis.text.toString()
            newQuestion.questionLoesung = activityQuestionCreateBinding.etLoesung.text.toString()


            val zone = ZoneId.of("UTC+2")
            newQuestion.erstellDatum = ZonedDateTime.now(zone)
            newQuestion.aenderungsDatum = ZonedDateTime.now(zone)
            newQuestion.isBeantwortet = true
            newQuestion.exam = actualExam

            lifecycleScope.launch(Dispatchers.IO) {
                val questionService = ServiceBuilder.buildService(QuestionService::class.java)

                try {
                    val response = questionService.addQuestion(examId, newQuestion)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            finish() // Move back to DestinationListActivity
                            Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            val intent = Intent(this, ExamDetailActivity::class.java)
            intent.putExtra("examId", examId) // Setze die aktuelle examId
            intent.putExtra("examObject", actualExam)
            navigateUpTo(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}