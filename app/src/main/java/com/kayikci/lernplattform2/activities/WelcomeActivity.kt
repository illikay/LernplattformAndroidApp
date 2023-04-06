package com.kayikci.lernplattform2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kayikci.lernplattform2.databinding.ActivityWelcomeBinding
import com.kayikci.lernplattform2.models.Exam
import com.kayikci.lernplattform2.models.LoginRequest
import com.kayikci.lernplattform2.models.LoginResponse
import com.kayikci.lernplattform2.services.AuthService
import com.kayikci.lernplattform2.services.ExamService
import com.kayikci.lernplattform2.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {
    private lateinit var activityWelcomeBinding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this



        activityWelcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(activityWelcomeBinding.root)

        activityWelcomeBinding.button.setOnClickListener {
            val intent = Intent(this, ExamListActivity::class.java)
            startActivity(intent)
            //finish()
        }

        activityWelcomeBinding.signInButton.setOnClickListener {
            val authenticationService = ServiceBuilder.buildService(AuthService::class.java)

            val email = activityWelcomeBinding.usernameEditText.text.toString()
            val password = activityWelcomeBinding.passwordEditText.text.toString()

            val loginRequest = LoginRequest(email, password)

            val requestCall = authenticationService.authenticate(loginRequest)

            requestCall.enqueue(object : Callback<LoginResponse> {

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        globalToken = response.body()?.token
                        System.out.println("this is the token: " + globalToken);
                        Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to login", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed to login", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    companion object {
        var globalToken: String? = ""
    }
}

