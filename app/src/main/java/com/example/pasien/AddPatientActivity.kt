package com.example.pasien

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pasien.model.AddPatientRequest
import com.example.pasien.network.RetrofitClient
import kotlinx.coroutines.launch

class AddPatientActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etDob: EditText
    private lateinit var etGender: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        etName = findViewById(R.id.etPatientName)
        etDob = findViewById(R.id.etPatientDob)
        etGender = findViewById(R.id.etPatientGender)
        etAddress = findViewById(R.id.etPatientAddress)
        etPhone = findViewById(R.id.etPatientPhone)
        btnSave = findViewById(R.id.btnSavePatient)

        btnSave.setOnClickListener {
            savePatient()
        }
    }

    private fun savePatient() {
        val name = etName.text.toString().trim()
        val dob = etDob.text.toString().trim()
        val gender = etGender.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val phone = etPhone.text.toString().trim()

        if (name.isEmpty() || dob.isEmpty() || gender.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Semua kolom formulir wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "").orEmpty()
        val tokenFormat = "Bearer $token"

        val request = AddPatientRequest(name, dob, gender, address, phone)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.addPatient(tokenFormat, request)

                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@AddPatientActivity, "Pasien berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddPatientActivity, "Gagal menyimpan: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AddPatientActivity, "Error jaringan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}