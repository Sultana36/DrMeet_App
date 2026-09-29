package com.example.drmeet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListDoctorActivity extends AppCompatActivity {

    private RecyclerView doctorRecyclerView;
    private ListDoctorAdapter listDoctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);

        String specialityName = getIntent().getStringExtra("SPECIALITY_NAME");

        doctorRecyclerView = findViewById(R.id.doctor_recycler_view);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ListDoctor> listDoctor = getDoctorsBySpeciality(specialityName);
        listDoctorAdapter = new ListDoctorAdapter(this, listDoctor);
        doctorRecyclerView.setAdapter(listDoctorAdapter);
    }

    private List<ListDoctor> getDoctorsBySpeciality(String specialityName) {
        List<ListDoctor> doctors = new ArrayList<>();

        if ("Cardiology".equals(specialityName)) {
            doctors.add(new ListDoctor(R.drawable.dr_david, "Dr. John Smith", "Cardiologist", 4.5));
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Alice Brown", "Cardiologist", 4.7));
            doctors.add(new ListDoctor(R.drawable.dr_david, "Dr. John Smith", "Cardiologist", 4.5));
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Alice Brown", "Cardiologist", 4.7));
        } else if ("Orthopedics".equals(specialityName)) {
            doctors.add(new ListDoctor(R.drawable.dr_sarah, "Dr. Sarah Johnson", "Orthopedic Surgeon", 4.8));
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Richard Lee", "Orthopedic Surgeon", 4.6));
            doctors.add(new ListDoctor(R.drawable.dr_sarah, "Dr. Sarah Johnson", "Orthopedic Surgeon", 4.8));
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Richard Lee", "Orthopedic Surgeon", 4.6));
        } else if ("Neurology".equals(specialityName)) {
            doctors.add(new ListDoctor(R.drawable.dr_jessica, "Dr. Jessica Lee", "Neurologist", 4.6));
            doctors.add(new ListDoctor(R.drawable.dr_david, "Dr. David White", "Neurologist", 4.4));
            doctors.add(new ListDoctor(R.drawable.dr_jessica, "Dr. Jessica Lee", "Neurologist", 4.6));
            doctors.add(new ListDoctor(R.drawable.dr_david, "Dr. David White", "Neurologist", 4.4));
        } else if ("Dental".equals(specialityName)) {
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Mike Anderson", "Dentist", 4.3));
            doctors.add(new ListDoctor(R.drawable.dr_sarah, "Dr. Sarah Brown", "Dentist", 4.7));
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Mike Anderson", "Dentist", 4.3));
            doctors.add(new ListDoctor(R.drawable.dr_sarah, "Dr. Sarah Brown", "Dentist", 4.7));
        } else if ("Radiology".equals(specialityName)) {
            doctors.add(new ListDoctor(R.drawable.dr_david, "Dr. Mary Jane", "Radiologist", 4.4));
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Alex Cooper", "Radiologist", 4.5));
            doctors.add(new ListDoctor(R.drawable.dr_david, "Dr. Mary Jane", "Radiologist", 4.4));
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Alex Cooper", "Radiologist", 4.5));
        } else {
            doctors.add(new ListDoctor(R.drawable.dr_sarah, "Dr. Alex Cooper", "General Practitioner", 4.0));
            doctors.add(new ListDoctor(R.drawable.dr_david, "Dr. Mary Jane", "General Practitioner", 4.4));
            doctors.add(new ListDoctor(R.drawable.dr_rich, "Dr. Alex Cooper", "General Practitioner", 4.5));
            doctors.add(new ListDoctor(R.drawable.dr_david, "Dr. Mary Jane", "General Practitioner", 4.4));
        }

        return doctors;
    }
}