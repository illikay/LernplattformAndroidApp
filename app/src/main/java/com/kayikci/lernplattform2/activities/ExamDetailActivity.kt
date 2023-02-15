package com.kayikci.lernplattform2.activities


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kayikci.lernplattform2.databinding.ActivityExamDetailBinding
import com.kayikci.lernplattform2.helpers.QuestionAdapter
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


class ExamDetailActivity : AppCompatActivity() {

    private lateinit var B: ActivityExamDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        B = ActivityExamDetailBinding.inflate(layoutInflater)
        setContentView(B.root)

        setSupportActionBar(B.detailToolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: Long = intent.getLongExtra("examId", 0)


        var actualExam:Exam? = null

        if (Build.VERSION.SDK_INT >= 33) {
            actualExam = intent.getParcelableExtra("examObject", Exam::class.java)
        } else {
            actualExam = intent.getParcelableExtra("examObject")
        }

        //Getting Questions from API

        // getting the recyclerview by its id
        val recyclerview = B.questionRecyclerView

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        val questionService = ServiceBuilder.buildService(QuestionService::class.java)

        val requestCall = questionService.getQuestionList(id)

        requestCall.enqueue(object: Callback<List<Question>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                if (response.isSuccessful) {
                    // Your status code is in the range of 200's
                    val questionList = response.body()!!

                    val adapter = QuestionAdapter(questionList, id )

                    // Setting the Adapter with the recyclerview
                    recyclerview.adapter = adapter


                } else if(response.code() == 401) {
                    Toast.makeText(this@ExamDetailActivity,
                        "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@ExamDetailActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                println(t.toString())
                Toast.makeText(this@ExamDetailActivity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })

        //loadQuestions(id, context, )

        loadDetails(id)

        initUpdateButton(id)

        initDeleteButton(id)

        B.questionfab.setOnClickListener {

            val intent = Intent(this@ExamDetailActivity, QuestionCreateActivity::class.java)
            intent.putExtra("examId", id)
            intent.putExtra("examObject", actualExam)


            startActivity(intent)
        }

    }

    private fun loadDetails(id: Long) {

        val examService = ServiceBuilder.buildService(ExamService::class.java)
        val requestCall = examService.getExam(id)

        requestCall.enqueue(object : retrofit2.Callback<Exam> {

            override fun onResponse(call: Call<Exam>, response: Response<Exam>) {

                if (response.isSuccessful) {
                    val exam = response.body()
                    exam?.let {
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

    private fun initUpdateButton(id: Long) {

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

    private fun initDeleteButton(id: Long) {

        B.btnDelete.setOnClickListener {

            val examService = ServiceBuilder.buildService(ExamService::class.java)
            val requestCall = examService.deleteExam(id)

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

    override fun onResume() {
        super.onResume()

        val id: Long = intent.getLongExtra("examId", 0)
        //loadQuestions(id)

        val questionService = ServiceBuilder.buildService(QuestionService::class.java)

        val requestCall = questionService.getQuestionList(id)

        requestCall.enqueue(object: Callback<List<Question>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                if (response.isSuccessful) {
                    // Your status code is in the range of 200's
                    val questionList = response.body()!!

                    val adapter = QuestionAdapter(questionList, id )

                    B.questionRecyclerView.adapter = adapter


                } else if(response.code() == 401) {
                    Toast.makeText(this@ExamDetailActivity,
                        "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@ExamDetailActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                println(t.toString())
                Toast.makeText(this@ExamDetailActivity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })


    }

    private fun loadQuestions(id:Long) {

        val context = this

        val questionService = ServiceBuilder.buildService(QuestionService::class.java)

        val filter = HashMap<String, String>()
//        filter["country"] = "India"
//        filter["count"] = "1"

        val requestCall = questionService.getQuestionList(id)

        requestCall.enqueue(object: Callback<List<Question>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                if (response.isSuccessful) {
                    // Your status code is in the range of 200's
                    val questionList = response.body()!!

                    var adapter = QuestionAdapter(questionList, id)


                    B.questionRecyclerView.adapter = adapter


                } else if(response.code() == 401) {
                    Toast.makeText(this@ExamDetailActivity,
                        "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@ExamDetailActivity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                println(t.toString())
                Toast.makeText(this@ExamDetailActivity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }


}

