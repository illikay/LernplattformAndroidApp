package com.kayikci.lernplattform2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kayikci.lernplattform2.databinding.ActivityUserRegisterBinding

class UserRegisterActivity : AppCompatActivity() {

    private lateinit var userRegisterBinding: ActivityUserRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userRegisterBinding = ActivityUserRegisterBinding.inflate(layoutInflater)
        setContentView(userRegisterBinding.root)

        userRegisterBinding.registerButton.setOnClickListener {
            val username = userRegisterBinding.usernameEditText.text.toString()
            val password = userRegisterBinding.passwordEditText.text.toString()
            val email = userRegisterBinding.emailEditText.text.toString()
            // Hier können Sie den neuen Benutzer registrieren
        }
    }
}
