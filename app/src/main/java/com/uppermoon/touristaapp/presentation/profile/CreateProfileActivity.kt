package com.uppermoon.touristaapp.presentation.profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.uppermoon.touristaapp.data.DestinationRepository
import com.uppermoon.touristaapp.data.UserResult
import com.uppermoon.touristaapp.data.network.api.ApiConfig
import com.uppermoon.touristaapp.data.preferences.UserPreferences
import com.uppermoon.touristaapp.data.preferences.ViewModelFactory
import com.uppermoon.touristaapp.databinding.ActivityCreateProfileBinding
import com.uppermoon.touristaapp.domain.User
import com.uppermoon.touristaapp.presentation.camera.CameraActivity
import com.uppermoon.touristaapp.reduceFileImage
import com.uppermoon.touristaapp.rotateFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class CreateProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var destinationRepository: DestinationRepository
    private lateinit var token: String
    private lateinit var user: User

    private lateinit var name: String
    private lateinit var nameUser: RequestBody
    private lateinit var age: String
    private lateinit var ageUser: RequestBody
    private lateinit var phoneNumber: String
    private lateinit var phoneNumberUser: RequestBody
    private lateinit var address: String
    private lateinit var addressUser: RequestBody
    private lateinit var lat: String
    private lateinit var latUser: RequestBody
    private lateinit var lon: String
    private lateinit var lonUser: RequestBody
    private lateinit var imageMultipart: MultipartBody.Part

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        val apiService = ApiConfig.getApiService()
        destinationRepository = DestinationRepository.getInstance(apiService)

        val pref = UserPreferences.getInstance(dataStore)
        val factory = ViewModelFactory.getInstance(this, destinationRepository, pref)
        profileViewModel = ViewModelProvider(this, factory).get(
            ProfileViewModel::class.java
        )

        profileViewModel.getToken().observe(this){
            user = it
            token = user.token
        }

        binding.btnCamera.setOnClickListener { startCameraX() }

        binding.btnCreateprof.setOnClickListener {
            name = binding.etName.text.toString()
            age = binding.etAge.text.toString()
            phoneNumber = binding.etPhonenumber.text.toString()
            address = binding.etAddress.text.toString()

            when {
                name == "" -> {
                    Toast.makeText(
                        this@CreateProfileActivity,
                        "Please fill this form!.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                age == "" -> {
                    Toast.makeText(
                        this@CreateProfileActivity,
                        "Please fill this form!.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                phoneNumber == "" -> {
                    Toast.makeText(
                        this@CreateProfileActivity,
                        "Please fill this form!.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                address == "" -> {
                    Toast.makeText(
                        this@CreateProfileActivity,
                        "Please fill this form!.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    createProfile()
                }
            }


        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            myFile?.let { file ->
                val reduceFile = reduceFileImage(file)
                rotateFile(file, isBackCamera)
                val requestImageFile = reduceFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                imageMultipart =
                    MultipartBody.Part.createFormData(
                        "photo",
                        file.name,
                        requestImageFile
                    )
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun createProfile() {
        nameUser = name.toRequestBody("text/plain".toMediaType())
        ageUser = age.toRequestBody("text/plain".toMediaType())
        phoneNumberUser = phoneNumber.toRequestBody("text/plain".toMediaType())
        addressUser = address.toRequestBody("text/plain".toMediaType())
        latUser = "2214.316656".toRequestBody()
        lonUser = "-12412.4221".toRequestBody()

        Log.d("IMAGE", imageMultipart.toString())
        Log.d("NAME", nameUser.toString())
        Log.d("AGE", ageUser.toString())
        Log.d("PHONE NUMBER", phoneNumberUser.toString())
        Log.d("ADDRESS", addressUser.toString())
        Log.d("LAT", latUser.toString())
        Log.d("LON", lonUser.toString())
        Log.d("token", token)

        profileViewModel.postCreateProfile(token, imageMultipart,nameUser, ageUser, phoneNumberUser, addressUser, latUser, latUser).observe(this){result ->
            when(result) {
                is UserResult.Success -> {
                    Toast.makeText(this, "Profile Created",Toast.LENGTH_SHORT).show()
                    finish()
                }
                is UserResult.Loading -> {
                    showLoading(true)
                }
                is UserResult.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}