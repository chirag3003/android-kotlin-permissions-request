package com.example.permissions

import android.Manifest
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
    private val locationAndCameraLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissions ->
            permissions.forEach(){
                val permissionName = it.key
                val isGranted = it.value
                if(isGranted){
                    when(permissionName){
                        android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            Toast.makeText(this,"Permission Granted For Location",Toast.LENGTH_SHORT)
                        }
                        android.Manifest.permission.CAMERA -> {
                            Toast.makeText(this,"Permission Granted For Camera",Toast.LENGTH_SHORT)
                        }
                    }
                }else{
                    when(permissionName){
                        android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                            Toast.makeText(this,"Permission Denied For Location",Toast.LENGTH_SHORT)
                        }
                        android.Manifest.permission.CAMERA -> {
                            Toast.makeText(this,"Permission Denied For Camera",Toast.LENGTH_SHORT)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.pBtn)
        btn.setOnClickListener() {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                showRationalDialog("Permission Required", "You need to provide camera permission");
            } else {
                locationAndCameraLauncher.launch(arrayOf(Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION))
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