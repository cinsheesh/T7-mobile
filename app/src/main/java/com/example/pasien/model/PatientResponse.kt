package com.example.pasien.model

data class PatientResponse(
    val success: Boolean,
    val message: String,
    val data: List<Patient>
)

data class Patient(
    val id: Int,
    val nama: String,
    val tanggal_lahir: String,
    val jenis_kelamin: String,
    val alamat: String,
    val no_telepon: String
)