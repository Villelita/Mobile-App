package com.zooquest.zooquest

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.zooquest.zooquest.databinding.ActivityCreateAccountBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.birthEditText.setText(format.format(calendar.time))
        }

        binding.birthEditText.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.maxDate = System.currentTimeMillis()
            }.show()
        }

        binding.createAccountBtn.setOnClickListener {
            val formularioValido = validarFormulario(
                binding.nameInputLayout,
                binding.emailInputLayout,
                binding.passwordInputLayout,
                binding.comfirmPasswordInputLayout,
                binding.birthInputLayout
            )

            if (formularioValido) {
                // Continuar con el proceso
            }
        }
    }

    fun validarFormulario(
        nombreInput: TextInputLayout,
        emailInput: TextInputLayout,
        passwordInput: TextInputLayout,
        confirmPasswordInput: TextInputLayout,
        birthInput: TextInputLayout
    ): Boolean {

        var isValid = true

        val nombre = nombreInput.editText?.text.toString().trim()
        val email = emailInput.editText?.text.toString().trim()
        val password = passwordInput.editText?.text.toString().trim()
        val confirmPassword = confirmPasswordInput.editText?.text.toString().trim()
        val birth = birthInput.editText?.text.toString().trim()

        // Validar Nombre
        if (nombre.isEmpty()) {
            nombreInput.error = "El nombre no puede estar vacío"
            isValid = false
        } else if (!nombre.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))) {
            nombreInput.error = "El nombre solo puede contener letras"
            isValid = false
        } else {
            nombreInput.error = null
        }

        // Validar Email
        if (email.isEmpty()) {
            emailInput.error = "El email no puede estar vacío"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Formato de email inválido"
            isValid = false
        } else {
            emailInput.error = null
        }

        // Validar Password
        if (password.isEmpty()) {
            passwordInput.error = "La contraseña no puede estar vacía"
            isValid = false
        } else if (password.length < 5) {
            passwordInput.error = "La contraseña debe tener más de 4 caracteres"
            isValid = false
        } else {
            passwordInput.error = null
        }

        // Validar Confirmación de Password
        if (confirmPassword.isEmpty()) {
            confirmPasswordInput.error = "La confirmación de Password no puede estar vacía"
            isValid = false
        } else if (password != confirmPassword) {
            confirmPasswordInput.error = "Los password no coinciden"
            isValid = false
        } else {
            confirmPasswordInput.error = null
        }

        // Validar Fecha Nacimiento
        if (birth.isEmpty()) {
            birthInput.error = "La fecha no puede estar vacía"
            isValid = false
        } else {
            birthInput.error = null
        }

        return isValid
    }
}