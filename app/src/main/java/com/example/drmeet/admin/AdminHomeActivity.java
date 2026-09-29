package com.example.drmeet.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drmeet.DeleteDoctorActivity;
import com.example.drmeet.InsertDoctorActivity;
import com.example.drmeet.PatientInformationListActivity;
import com.example.drmeet.R;
import com.example.drmeet.UpdateDoctorActivity;
import com.example.drmeet.ViewDoctorActivity;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnInsertPlace = findViewById(R.id.btn_insert_place);
        Button btnViewPlace = findViewById(R.id.btn_view_place);
        Button btnUpdatePlace = findViewById(R.id.btn_update_place);
        Button btnDeletePlace = findViewById(R.id.btn_delete_place);
        Button btn_view_patient = findViewById(R.id.btn_view_patient);

        btnInsertPlace.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, InsertDoctorActivity.class)));
        btnViewPlace.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, ViewDoctorActivity.class)));
        btnUpdatePlace.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, UpdateDoctorActivity.class)));
        btnDeletePlace.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, DeleteDoctorActivity.class)));
        btn_view_patient.setOnClickListener(v -> startActivity(new Intent(AdminHomeActivity.this, PatientInformationListActivity.class)));
    }
}
