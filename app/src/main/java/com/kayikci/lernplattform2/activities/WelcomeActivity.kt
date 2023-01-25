package com.kayikci.lernplattform2.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.kayikci.lernplattform2.databinding.ActivityWelcomeBinding


import com.kayikci.lernplattform2.services.MessageService
import com.kayikci.lernplattform2.services.ServiceBuilder


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {
    private lateinit var B: ActivityWelcomeBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
        B = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(B.root)


		// To be replaced by retrofit code

        val messageService = ServiceBuilder.buildService(MessageService::class.java)
        val requestCall = messageService.getMessages("http://10.0.2.2:7634/")

        requestCall.enqueue(object: Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val msg = response.body()
                    msg?.let {
                        B.message.text = msg
                    }
                } else {
                    Toast.makeText(this@WelcomeActivity,
                        "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@WelcomeActivity,
                    "Failed to retrieve items", Toast.LENGTH_LONG).show()
            }

        })
	}

	fun getStarted(view: View) {
		val intent = Intent(this, ExamListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
