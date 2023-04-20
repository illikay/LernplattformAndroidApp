package com.kayikci.lernplattform2.activities


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kayikci.lernplattform2.R
import com.kayikci.lernplattform2.databinding.ActivityExamListBinding
import com.kayikci.lernplattform2.helpers.ExamAdapter
import com.kayikci.lernplattform2.services.ExamService
import com.kayikci.lernplattform2.services.ServiceBuilder
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {

            val examService = ServiceBuilder.buildService(ExamService::class.java)


            try {
                val response = examService.getExamListByUser()
                if (response.isSuccessful) {
                    // Your status code is in the range of 200's
                    val destinationList = response.body()!!
                    activityExamListBinding.examRecyclerView.adapter =
                        ExamAdapter(destinationList)
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
            } catch (e: Exception) {
                println(e.toString())
                Toast.makeText(this@ExamListActivity, "Error Occurred $e", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_logout -> {
                val intent = Intent(this@ExamListActivity, WelcomeActivity::class.java)
                finish()
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return true
    }
}









