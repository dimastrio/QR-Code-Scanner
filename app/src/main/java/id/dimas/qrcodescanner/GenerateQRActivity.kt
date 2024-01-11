package id.dimas.qrcodescanner

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import id.dimas.qrcodescanner.databinding.ActivityGenerateQrBinding
import java.io.File
import java.io.FileOutputStream


class GenerateQRActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGenerateQrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerateQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        generateQR()
    }


    private fun generateQR() {
        val multi = MultiFormatWriter()
        binding.btnGenerate.setOnClickListener {
            try {
                val code: String = binding.etText.text.toString()
                val bitMatrix: BitMatrix = multi.encode(code, BarcodeFormat.QR_CODE, 300, 300)
                val barcodeEncoder = BarcodeEncoder()
                val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
                binding.ivQRCode.setImageBitmap(bitmap)
                binding.btnDownload.isGone = false
                binding.btnDownload.setOnClickListener {
                    downloadImage()
                }
            } catch (e: WriterException) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadImage() {
        //to get the image from the ImageView (say iv)
        //to get the image from the ImageView (say iv)
        val bitmapDrawable: BitmapDrawable = binding.ivQRCode.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap


//        try {
//            val root = Environment.getExternalStorageDirectory().toString()
//            val file = File("$root/myImagesDGS.jpg")
//            val out = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
//            out.flush()
//            out.close()
//        } catch (ex: java.lang.Exception) {
//            ex.printStackTrace()
//        }


        var outStream: FileOutputStream? = null
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//        val file = File(this.getFileStreamPath("FileName").path)
        val dir = File("$file")
        dir.mkdirs()
        val fileName = String.format("%d.png", System.currentTimeMillis())
        val outFile = File(dir, fileName)
        try {
            outStream = FileOutputStream(outFile)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        try {
            outStream?.flush()
            Toast.makeText(this, "Berhasil download", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            outStream?.close()

        }
    }
}