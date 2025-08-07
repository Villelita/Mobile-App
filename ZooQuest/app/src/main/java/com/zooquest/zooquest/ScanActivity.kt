package com.zooquest.zooquest

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScanActivity : AppCompatActivity() {

    private lateinit var nfcAdapter: NfcAdapter
    private var pendingIntent: PendingIntent? = null
    private lateinit var nfcIntentFilters: Array<IntentFilter>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            Toast.makeText(this, "Este dispositivo no tiene NFC", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        if (!nfcAdapter.isEnabled) {
            Toast.makeText(this, "Por favor activa NFC en configuración", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
       if (::nfcAdapter.isInitialized && nfcAdapter.isEnabled) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilters, null)
        } else {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        }

        val options = Bundle()
        nfcAdapter.enableReaderMode(
            this,
            { tag ->
                runOnUiThread {
                    startActivity(Intent(this@ScanActivity, AnimalActivity::class.java))
                }
            },
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V,
            options
        )
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableReaderMode(this)
    }

}
