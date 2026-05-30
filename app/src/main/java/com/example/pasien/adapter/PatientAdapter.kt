package com.example.pasien

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pasien.model.Patient

class PatientAdapter(
    private val patientList: List<Patient>
) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    // 1. Membuat wadah untuk menampung layout item_patient.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    // 2. Menghubungkan data pasien dengan komponen teks di layout
    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patientList[position]
        holder.tvName.text = patient.nama
        holder.tvGender.text = "Jenis Kelamin: ${patient.jenis_kelamin}"
        holder.tvAddress.text = "Alamat: ${patient.alamat}"
    }

    // 3. Menghitung jumlah total data pasien yang ada di list
    override fun getItemCount(): Int {
        return patientList.size
    }

    // Class ViewHolder untuk mengenali ID komponen di dalam item_patient.xml
    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvPatientName)
        val tvGender: TextView = itemView.findViewById(R.id.tvPatientGender)
        val tvAddress: TextView = itemView.findViewById(R.id.tvPatientAddress)
    }
}