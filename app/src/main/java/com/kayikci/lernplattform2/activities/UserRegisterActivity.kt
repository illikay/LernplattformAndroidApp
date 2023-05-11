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
import com.kayikci.lernplattform2.databinding.ActivityUserRegisterBinding
import com.kayikci.lernplattform2.models.RegisterRequest
import com.kayikci.lernplattform2.services.AuthService
import com.kayikci.lernplattform2.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserRegisterActivity : AppCompatActivity() {

    private lateinit var userRegisterBinding: ActivityUserRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        userRegisterBinding = ActivityUserRegisterBinding.inflate(layoutInflater)
        setContentView(userRegisterBinding.root)

        setSupportActionBar(userRegisterBinding.detailToolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userRegisterBinding.registerButton.setOnClickListener {
            val firstname = userRegisterBinding.firstnameEditText.text.toString()
            val lastname = userRegisterBinding.lastnameEditText.text.toString()
            val email = userRegisterBinding.emailEditText.text.toString()
            val password = userRegisterBinding.passwordEditText.text.toString()


            if (!validateUsername() || !validateEmail() || !validatePassword()) {
                return@setOnClickListener
            }

           val registerRequest = RegisterRequest(firstname,lastname,email,password)

            lifecycleScope.launch(Dispatchers.IO) {

                val authenticationService = ServiceBuilder.buildService(AuthService::class.java)

                try {
                    val response = authenticationService.register(registerRequest)
                    if (response.isSuccessful) {
                        WelcomeActivity.globalToken = response.body()?.token
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Successfully registered User", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this@UserRegisterActivity, WelcomeActivity::class.java)
                            startActivity(intent)
                        }

                    } else {
                        withContext(Dispatchers.Main) {
                           Toast.makeText(
                                context,
                               response.errorBody()?.string()
                                , Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to register User", Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }
    }

    private fun validatePassword(): Boolean {
        val password: String = userRegisterBinding.passwordEditText.text.toString().trim()
        val checkPassword = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!?@#$%^&+=])(?=\\S+$).{8,}$")
        //val checkSpaces = Regex("[^\\s]+")

        return if (password.isEmpty()) {
            userRegisterBinding.passwordEditText.error = "Passwort darf nicht leer sein"
            false
        }
        else if (!password.matches(checkPassword)) {
            userRegisterBinding.passwordEditText.error = "Bitte ein Passwort, das mindestens 1 Großbuchstabe, 1 Kleinbuchstabe, 1 Ziffer, " +
                    "1 Sonderzeichen enthält, keine Leerzeichen enthält und eine Länge von mindestens 10 hat eingeben"
            false
        }

        else {
            userRegisterBinding.passwordEditText.error = null
            true
        }
    }

    private fun validateUsername(): Boolean {
        val firstName: String = userRegisterBinding.firstnameEditText.text.toString().trim()
        val lastName: String = userRegisterBinding.lastnameEditText.text.toString().trim()
        val checkSpaces = Regex("[^\\s]+")

        return if (firstName.isEmpty()) {
            userRegisterBinding.firstnameEditText.error = "Vorname darf nicht leer sein"
            false
        }
        else if (lastName.isEmpty()) {
            userRegisterBinding.lastnameEditText.error = "Nachname darf nicht leer sein"
            false
        }
        else if (firstName.length > 20) {
            userRegisterBinding.firstnameEditText.error = "Vorname ist zu lang"
            false
        }
        else if (lastName.length > 20) {
            userRegisterBinding.lastnameEditText.error = "Nachname ist zu lang"
            false
        }
        else if (!firstName.matches(checkSpaces)) {
            userRegisterBinding.firstnameEditText.error = "In Vorname sind keine Leerzeichen erlaubt"
            false
        }
        else if (!lastName.matches(checkSpaces)) {
            userRegisterBinding.lastnameEditText.error = "In Nachname sind keine Leerzeichen erlaubt"
            false
        }
        else {
            userRegisterBinding.firstnameEditText.error = null
            userRegisterBinding.lastnameEditText.error = null
            true
        }
    }

    private fun validateEmail(): Boolean {
        val email: String = userRegisterBinding.emailEditText.text.toString().trim()
        val checkEmail = Regex("[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+")
        return if (email.isEmpty()) {
            userRegisterBinding.emailEditText.error = "E-mail darf nicht leer sein"
            false
        } else if (!email.matches(checkEmail)) {
            userRegisterBinding.emailEditText.error = "Das ist keine gültige E-mail"
            false
        } else {
            userRegisterBinding.emailEditText.error = null
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_logout -> {
                val intent = Intent(this@UserRegisterActivity, WelcomeActivity::class.java)
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
