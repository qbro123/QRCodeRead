package vnext.tuantq.qrcameracode

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import vnext.tuantq.qrcameracode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), QRCodeReaderView.OnQRCodeReadListener {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()
        binding.qrdecoderview.setOnQRCodeReadListener(this)
        binding.qrdecoderview.setBackCamera()
        binding.button.setOnClickListener {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
    }




    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 11)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            11 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Đã bật quyền", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.qrdecoderview.startCamera()
    }

    override fun onPause() {
        super.onPause()
        binding.qrdecoderview.stopCamera()
    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        Log.d("123123", "onQRCodeRead: $text")
    }
}