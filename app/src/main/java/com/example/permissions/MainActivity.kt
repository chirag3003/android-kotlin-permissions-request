package com.example.permissions

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private val activityResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.pBtn)
        btn.setOnClickListener() {
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
                showRationalDialog("Permission Required","You need to provide camera permission");
            }else{
                activityResultLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    private fun showRationalDialog(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
        }
        builder.create().show()
    }
}