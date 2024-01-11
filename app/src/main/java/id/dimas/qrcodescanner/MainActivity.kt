package id.dimas.qrcodescanner

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.dimas.qrcodescanner.databinding.ActivityMainBinding
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        binding.qrScanner.setOnClickListener {
            toScannerCamera()
        }
        binding.qrGenerate.setOnClickListener {
            toGenerateQR()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        permission()
    }

    private fun permission() {
        val perms = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(this, *perms)) {
            binding.qrScanner.setOnClickListener {
                toScannerCamera()
            }
            binding.qrGenerate.setOnClickListener {
                toGenerateQR()
            }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, "Ijinkan akses",
                100, *perms
            )
        }
    }

    private fun toScannerCamera() {
        val intent = Intent(this, ScannerActivity::class.java)
        startActivity(intent)

    }

    private fun toGenerateQR() {
        val intent = Intent(this, GenerateQRActivity::class.java)
        startActivity(intent)
    }
}