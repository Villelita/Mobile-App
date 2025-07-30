package com.zooquest.zooquest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zooquest.zooquest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.scanBtn.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

        onBackPressedDispatcher.addCallback(this) {
            mostrarDialogoCerrarSesion(this@MainActivity, {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            })
        }
    }

    fun mostrarDialogoCerrarSesion(context: Context, onConfirmar: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                onConfirmar() // Acción a realizar al confirmar
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}