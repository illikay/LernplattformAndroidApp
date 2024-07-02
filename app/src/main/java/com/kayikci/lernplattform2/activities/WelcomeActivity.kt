package com.kayikci.lernplattform2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kayikci.lernplattform2.databinding.ActivityWelcomeBinding
import com.kayikci.lernplattform2.models.LoginRequest
import com.kayikci.lernplattform2.services.AuthService
import com.kayikci.lernplattform2.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WelcomeActivity : AppCompatActivity() {
    private lateinit var activityWelcomeBinding: ActivityWelcomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        activityWelcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(activityWelcomeBinding.root)


        activityWelcomeBinding.registerButton.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, UserRegisterActivity::class.java)
            startActivity(intent)

        }



        activityWelcomeBinding.signInButton.setOnClickListener {


            val email = activityWelcomeBinding.usernameEditText.text.toString()
            val password = activityWelcomeBinding.passwordEditText.text.toString()


            val loginRequest = LoginRequest(email, password)

            lifecycleScope.launch(Dispatchers.IO) {

                val authenticationService = ServiceBuilder.buildService(AuthService::class.java)

                try {
                    val response = authenticationService.authenticate(loginRequest)
                    if (response.isSuccessful) {
                        globalToken = response.body()?.token
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this@WelcomeActivity, ExamListActivity::class.java)
                            startActivity(intent)
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Failed to login", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to login", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }


    companion object {
        var globalToken: String? = ""
    }
}

