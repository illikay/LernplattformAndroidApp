package com.kayikci.lernplattform2.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kayikci.lernplattform2.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var activityWelcomeBinding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWelcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(activityWelcomeBinding.root)

        activityWelcomeBinding.button.setOnClickListener {
            val intent = Intent(this, ExamListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
