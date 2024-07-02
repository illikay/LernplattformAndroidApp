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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class ExamDetailActivity : AppCompatActivity() {

    private lateinit var activityExamDetailBinding: ActivityExamDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityExamDetailBinding = ActivityExamDetailBinding.inflate(layoutInflater)
        setContentView(activityExamDetailBinding.root)

        setSupportActionBar(activityExamDetailBinding.detailToolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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


        val examId = intent.getLongExtra("examId", 0)
        println("Exam ID in onCreate: $examId")

        if (examId != 0L) {
            loadDetails(examId)

            loadQuestions(examId)

            initUpdateButton(examId)

            initDeleteButton(examId)
        } else {
            println("Received examId is 0 in onCreate")
        }



        activityExamDetailBinding.questionfab.setOnClickListener {

            val intent = Intent(this@ExamDetailActivity, QuestionCreateActivity::class.java)
            intent.putExtra("examId", examId)
            intent.putExtra("examObject", actualExam)


            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        val examId = intent.getLongExtra("examId", 0)
        val actualExam: Exam?
        if (Build.VERSION.SDK_INT >= 33) {
            actualExam = intent.getParcelableExtra("examObject", Exam::class.java)
        } else {
            @Suppress("DEPRECATION") actualExam = intent.getParcelableExtra("examObject")
        }
        if (examId != 0L) {
            println("Reloading exams in onResume with ID: $examId")
            loadDetails(examId)
            loadQuestions(examId)
            initUpdateButton(examId)
            initDeleteButton(examId)
        } else {
            println("Received examId is 0 in onResume")
        }
    }


    private fun loadDetails(id: Long) {

        lifecycleScope.launch(Dispatchers.IO) {

            val examService = ServiceBuilder.buildService(ExamService::class.java)



            try {
                val response = examService.getExam(id)
                if (response.isSuccessful) {
                    val exam = response.body()
                    withContext(Dispatchers.Main) {
                        exam?.let {


                            activityExamDetailBinding.etName.setText(exam.pruefungsName)
                            activityExamDetailBinding.etInfo.setText(exam.info)
                            activityExamDetailBinding.etBeschreibung.setText(exam.beschreibung)

                            val utcErstellDatum: ZonedDateTime? = exam.erstellDatum
                            val utcAenderungsDatum: ZonedDateTime? = exam.aenderungsDatum
                            val germanErstelldatum: ZonedDateTime? =
                                utcErstellDatum?.withZoneSameInstant(
                                    ZoneId.of("Europe/Berlin")
                                )
                            val germanAenderunsdatum: ZonedDateTime? =
                                utcAenderungsDatum?.withZoneSameInstant(
                                    ZoneId.of("Europe/Berlin")
                                )
                            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                            val formattedErstelldatum: String? =
                                germanErstelldatum?.format(formatter)
                            val formattedAenderungsdatum: String? =
                                germanAenderunsdatum?.format(formatter)


                            activityExamDetailBinding.tvErstellDatum.setText("Erstelldatum: " + formattedErstelldatum)

                            activityExamDetailBinding.tvAenderungsdatum.setText("Änderungsdatum: " + formattedAenderungsdatum)

                            activityExamDetailBinding.collapsingToolbar.title = exam.pruefungsName
                        }
                    }


                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ExamDetailActivity,
                            "Failed to retrieve details",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ExamDetailActivity,
                        "Failed to retrieve details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    @SuppressLint("WeekBasedYear")
    private fun initUpdateButton(id: Long) {

        activityExamDetailBinding.btnUpdate.setOnClickListener {

            val actualExam: Exam?

            if (Build.VERSION.SDK_INT >= 33) {
                actualExam = intent.getParcelableExtra("examObject", Exam::class.java)
            } else {
                @Suppress("DEPRECATION") actualExam = intent.getParcelableExtra("examObject")
            }

            actualExam?.pruefungsName = activityExamDetailBinding.etName.text.toString()
            actualExam?.info = activityExamDetailBinding.etInfo.text.toString()
            actualExam?.beschreibung = activityExamDetailBinding.etBeschreibung.text.toString()

            //nur Änderungsdatum wird aktualisiert
            actualExam?.aenderungsDatum = ZonedDateTime.now()

            println("das ist die asdf aktuelle Exam: " + actualExam.toString())
            println("das ist die asdf aktuelle examId: $id")

            lifecycleScope.launch(Dispatchers.IO) {

                val examService = ServiceBuilder.buildService(ExamService::class.java)


                try {
                    val response = examService.updateExam(id, actualExam!!)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            finish() // Move back to DestinationListActivity
                            Toast.makeText(
                                this@ExamDetailActivity,
                                "Item Updated Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@ExamDetailActivity, "Failed to update item", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ExamDetailActivity, "Failed to update item", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }

        }
    }

    private fun initDeleteButton(id: Long) {

        activityExamDetailBinding.btnDelete.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                val examService = ServiceBuilder.buildService(ExamService::class.java)


                try {
                    val response = examService.deleteExam(id)
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            finish() // Move back to DestinationListActivity
                            Toast.makeText(
                                this@ExamDetailActivity, "Successfully Deleted", Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@ExamDetailActivity, "Failed to Delete", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ExamDetailActivity, "Failed to Delete", Toast.LENGTH_SHORT
                        ).show()
                    }
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


    private fun loadQuestions(id: Long) {

        lifecycleScope.launch(Dispatchers.IO) {

            val questionService = ServiceBuilder.buildService(QuestionService::class.java)

            val actualExam: Exam?

            if (Build.VERSION.SDK_INT >= 33) {
                actualExam = intent.getParcelableExtra("examObject", Exam::class.java)
            } else {
                @Suppress("DEPRECATION") actualExam = intent.getParcelableExtra("examObject")
            }

            try {
                val response = questionService.getQuestionList(id)
                if (response.isSuccessful) {
                    val questionList = response.body()!!
                    val adapter = QuestionAdapter(questionList, id, actualExam)
                    withContext(Dispatchers.Main) {

                        activityExamDetailBinding.questionRecyclerView.adapter = adapter
                    }


                } else if (response.code() == 401) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ExamDetailActivity,
                            "Your session has expired. Please Login again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {// Application-level failure
                        // Your status code is in the range of 300's, 400's and 500's
                        Toast.makeText(
                            this@ExamDetailActivity, "Failed to retrieve items", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ExamDetailActivity, "Error Occurred $e", Toast.LENGTH_LONG)
                        .show()
                }
            }

        }

    }


}

