package com.example.drmeet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.drmeet.Database.DatabaseHelper;

import java.util.List;

public class ViewDoctorActivity extends Activity {

    private ListView listView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);

        listView = findViewById(R.id.list_view_doctors);
        databaseHelper = new DatabaseHelper(this);

        List<Doctor> doctorList = databaseHelper.getAllDoctors();
        DoctorAdapter adapter = new DoctorAdapter(this, doctorList);
        listView.setAdapter(adapter);
    }
}
