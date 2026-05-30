package com.example.pasien

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pasien.network.RetrofitClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var rvPatients: RecyclerView
    private lateinit var tvName: TextView

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvName = findViewById(R.id.tvName)
        rvPatients = findViewById(R.id.rvPatients)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val btnAddPatient = findViewById<FloatingActionButton>(R.id.btnAddPatient)

        rvPatients.layoutManager = LinearLayoutManager(this)

        val name = intent.getStringExtra(EXTRA_NAME).orEmpty()
        tvName.text = name

        btnAddPatient.setOnClickListener {
            val intent = Intent(this, AddPatientActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchPatientData()
    }

    private fun fetchPatientData() {
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "").orEmpty()
        val tokenFormat = "Bearer $token"

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPatients(tokenFormat)

                if (response.isSuccessful) {
                    val patientResponse = response.body()
                    val listPasien = patientResponse?.data ?: emptyList()

                    val adapter = PatientAdapter(listPasien)
                    rvPatients.adapter = adapter
                } else {
                    Toast.makeText(this@HomeActivity, "Gagal mengambil data pasien", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HomeActivity, "Terjadi kesalahan jaringan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Logout") { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        prefs.edit().remove("token").apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}