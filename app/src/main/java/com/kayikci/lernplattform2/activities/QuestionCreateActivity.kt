package com.kayikci.lernplattform2.activities

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kayikci.lernplattform2.databinding.ActivityQuestionCreateBinding
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.Question
import com.kayikci.lernplattform2.services.ExamService
import com.kayikci.lernplattform2.services.QuestionService
import com.kayikci.lernplattform2.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class QuestionCreateActivity : AppCompatActivity() {
    private lateinit var B: ActivityQuestionCreateBinding
    private val counter: AtomicInteger = AtomicInteger()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        B = ActivityQuestionCreateBinding.inflate(layoutInflater)
        setContentView(B.root)

        setSupportActionBar(B.detailToolbar)

        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val examId = intent.getLongExtra("examIdtoQuestionCreate", 0)

        var actualExam:Exam? = null

        if (Build.VERSION.SDK_INT >= 33) {
            actualExam = intent.getParcelableExtra("actualExam", Exam::class.java)
        } else {
            actualExam = intent.getParcelableExtra("actualExam")
        }



        B.btnQuestionAdd.setOnClickListener {
            var newQuestion = Question()
            newQuestion.questionFrage = B.etFragestellung.text.toString()
            newQuestion.questionHinweis = B.etHinweis.text.toString()
            newQuestion.questionLoesung = B.etLoesung.text.toString()


            val format = SimpleDateFormat("dd.MM.YYYY HH:mm:ss")
            val dateString: String = format.format(Date())
            newQuestion.erstellDatum = dateString
            newQuestion.aenderungsDatum = dateString
            newQuestion.isBeantwortet = true
            newQuestion.exam = actualExam

            val questionService = ServiceBuilder.buildService(QuestionService::class.java)
            val requestCall = questionService.addQuestion(examId, newQuestion)

            requestCall.enqueue(object: Callback<Question> {

                override fun onResponse(call: Call<Question>, response: Response<Question>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        var newlyCreatedExam = response.body() // Use it or ignore it
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