package id.dimas.qrcodescanner

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.dimas.qrcodescanner.databinding.ActivityQrscanBinding

class QRScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrscanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrscanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showResult()
        setButtonCopy()
        setButtonBack()
    }

    private fun showResult() {
        val result = intent.getStringExtra("resultQR")
        val resultError = intent.getStringExtra("resultError")
        val alertDialog = AlertDialog.Builder(this).create()

        if (resultError.isNullOrEmpty()) {
            binding.tvResult.text = result
        } else {
            alertDialog.setMessage(resultError)
            alertDialog.show()
        }
    }

//    private fun showResultError(){
//        val alertDialog = AlertDialog.Builder(this).create()
//        val resultError = intent.getStringExtra("resultError")
//
//
//    }

    private fun setButtonCopy() {
        binding.btnCopy.setOnClickListener {
            if (binding.tvResult.text.isNotEmpty()) {
                val result = binding.tvResult.text.toString()
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip: ClipData = ClipData.newPlainText("resultQR", result)
                clipBoard.setPrimaryClip(clip)
                Toast.makeText(this, "Salin berhasil", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setButtonBack() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}