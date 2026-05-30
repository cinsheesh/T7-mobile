package com.example.pasien.network

import com.example.pasien.model.LoginRequest
import com.example.pasien.model.LoginResponse
import com.example.pasien.model.PatientResponse
import com.example.pasien.model.AddPatientRequest
import com.example.pasien.model.AddPatientResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("pasien")
    suspend fun getPatients(
        @Header("Authorization") token: String
    ): Response<PatientResponse>

    @POST("pasien")
    suspend fun addPatient(
        @Header("Authorization") token: String,
        @Body request: AddPatientRequest
    ): Response<AddPatientResponse>
}