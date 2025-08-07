package com.zooquest.zooquest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.zooquest.zooquest.data.UsersViewModelFactory
import com.zooquest.zooquest.data.database.AppDatabase
import com.zooquest.zooquest.databinding.ActivityLoginBinding
import com.zooquest.zooquest.repository.UsersRepository
import com.zooquest.zooquest.viewModel.UsersViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: UsersViewModel by viewModels {
        val dao = AppDatabase.getInstance(applicationContext).userDAO()
        val repo = UsersRepository(dao)
        UsersViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("nombre_usuario")
        editor.apply()

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
                viewModel.doLogin(
                    binding.emailInputLayout.editText?.text.toString().trim(),
                    binding.passwordInputLayout.editText?.text.toString().trim()
                )
            }
        }

        viewModel.user.observe(this) {
            if (it != null) {
                val sharedPref = getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("nombre_usuario", it.name)
                editor.apply()

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
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