package com.kayikci.lernplattform2.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kayikci.lernplattform2.databinding.ActivityQuestionCreateBinding
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question
import com.kayikci.lernplattform2.services.QuestionService
import com.kayikci.lernplattform2.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class QuestionCreateActivity : AppCompatActivity() {
    private lateinit var activityQuestionCreateBinding: ActivityQuestionCreateBinding

    @SuppressLint("WeekBasedYear")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityQuestionCreateBinding = ActivityQuestionCreateBinding.inflate(layoutInflater)
        setContentView(activityQuestionCreateBinding.root)

        setSupportActionBar(activityQuestionCreateBinding.detailToolbar)

        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val examId = intent.getLongExtra("examId", 0)


        val actualExam: Exam?

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


            val format = SimpleDateFormat("dd.MM.YYYY HH:mm:ss", Locale.GERMANY)
            val dateString: String = format.format(Date())
            newQuestion.erstellDatum = dateString
            newQuestion.aenderungsDatum = dateString
            newQuestion.isBeantwortet = true
            newQuestion.exam = actualExam

            val questionService = ServiceBuilder.buildService(QuestionService::class.java)
            val requestCall = questionService.addQuestion(examId, newQuestion)

            requestCall.enqueue(object : Callback<Question> {

                override fun onResponse(call: Call<Question>, response: Response<Question>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Question>, t: Throwable) {
                    Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            })
        }


    }

}