package com.kayikci.lernplattform2.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast


import com.kayikci.lernplattform2.databinding.ActivityExamListBinding
import com.kayikci.lernplattform2.helpers.ExamAdapter


import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.services.ExamService
import com.kayikci.lernplattform2.services.ServiceBuilder


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamListActivity : AppCompatActivity() {

    private lateinit var activityExamListBinding: ActivityExamListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityExamListBinding = ActivityExamListBinding.inflate(layoutInflater)
        setContentView(activityExamListBinding.root)

        setSupportActionBar(activityExamListBinding.detailToolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //setSupportActionBar(activityExamListBinding.toolbar)
        //activityExamListBinding.toolbar.title = title

        activityExamListBinding.fab.setOnClickListener {
            val intent = Intent(this@ExamListActivity, ExamCreateActivity::class.java)

            startActivity(intent)
        }
    }



    override fun onResume() {
        super.onResume()

        loadDestinations()
    }

    private fun loadDestinations() {

        val examService = ServiceBuilder.buildService(ExamService::class.java)


        val requestCall = examService.getExamList()

        requestCall.enqueue(object : Callback<List<Exam>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<Exam>>, response: Response<List<Exam>>) {
                if (response.isSuccessful) {
                    // Your status code is in the range of 200's
                    val destinationList = response.body()!!
                    activityExamListBinding.examRecyclerView.adapter = ExamAdapter(destinationList)
                } else if (response.code() == 401) {
                    Toast.makeText(
                        this@ExamListActivity,
                        "Your session has expired. Please Login again.", Toast.LENGTH_LONG
                    ).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(
                        this@ExamListActivity,
                        "Failed to retrieve items",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<Exam>>, t: Throwable) {
                println(t.toString())
                Toast.makeText(this@ExamListActivity, "Error Occurred $t", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}









