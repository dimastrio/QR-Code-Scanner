package id.dimas.qrcodescanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import id.dimas.qrcodescanner.databinding.ActivityScannerBinding


class ScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScannerBinding

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scannerFunc()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


    private fun scannerFunc() {

        val scanner = binding.scannerView

        codeScanner = CodeScanner(this, scanner)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {

            val intent = Intent(this, QRScanActivity::class.java)
            intent.putExtra("resultQR", it.text)
            startActivity(intent)
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            val intent = Intent(this, QRScanActivity::class.java)
            intent.putExtra("resultError", it.message)
            startActivity(intent)
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }


}