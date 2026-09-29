package com.example.drmeet;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drmeet.Database.DatabaseHelper;

public class PatientInformationListActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private PatientInformationListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information_list);

        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        Cursor cursor = databaseHelper.getAllPatient();
        adapter = new PatientInformationListAdapter(this, cursor);
        listView.setAdapter(adapter);

    }
}