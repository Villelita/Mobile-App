package com.zooquest.zooquest

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.zooquest.zooquest.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newAccountTxt.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }

        binding.roundedBlackButton.setOnClickListener {
            val esValido = validarLogin(
                binding.emailInputLayout,
                binding.passwordInputLayout
            )

            if (esValido) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Mostrar errores
            }

        }
    }

    private fun validarLogin(
        emailInput: TextInputLayout,
        passwordInput: TextInputLayout
    ): Boolean {

        var isValid = true

        val email = emailInput.editText?.text.toString().trim()
        val password = passwordInput.editText?.text.toString().trim()

        // Validación Email
        if (email.isEmpty()) {
            emailInput.error = "El email no puede estar vacío"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Formato de email inválido"
            isValid = false
        } else {
            emailInput.error = null
        }

        // Validación Password
        if (password.isEmpty()) {
            passwordInput.error = "La contraseña no puede estar vacía"
            isValid = false
        } else if (password.length < 5) {
            passwordInput.error = "La contraseña debe tener al menos 5 caracteres"
            isValid = false
        } else {
            passwordInput.error = null
        }

        return isValid
    }
}