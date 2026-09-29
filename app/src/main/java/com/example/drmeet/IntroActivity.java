package com.example.drmeet;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drmeet.Database.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;


public class IntroActivity extends AppCompatActivity {

    private RecyclerView specialityRecycler, topDoctorsRecycler;
    private SpecialityAdapter specialityAdapter;
    private TopDoctorsAdapter topDoctorsAdapter;
    private Button seeAllButton; // Added for "See all" button
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        databaseHelper = new DatabaseHelper(this);
        // Initialize RecyclerViews
        specialityRecycler = findViewById(R.id.speciality_recycler);
        topDoctorsRecycler = findViewById(R.id.top_doctors_recycler);
        seeAllButton = findViewById(R.id.see_all_button); // Initialize "See all" button

        // Set up Doctor Speciality RecyclerView
        specialityRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        specialityAdapter = new SpecialityAdapter(getSpecialities());
        specialityRecycler.setAdapter(specialityAdapter);

        // Set up Top Doctors RecyclerView
        List<Doctor> doctorList = databaseHelper.getAllDoctors();
        topDoctorsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //topDoctorsAdapter = new TopDoctorsAdapter(doctorList);
        topDoctorsAdapter = new TopDoctorsAdapter(getTopDoctors());
        topDoctorsRecycler.setAdapter(topDoctorsAdapter);

        // Handle "See all" button click
        seeAllButton.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, ViewDoctorActivity.class);
            startActivity(intent);
        });

        // Speciality item click handling
        specialityAdapter.setOnItemClickListener(speciality -> {
            Toast.makeText(this, "Clicked on " + speciality.getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(IntroActivity.this, ListDoctorActivity.class);
            intent.putExtra("SPECIALITY_NAME", speciality.getName());
            startActivity(intent);
        });

        // Top Doctors item click handling
        topDoctorsAdapter.setOnItemClickListener(topDoctor -> {
            Toast.makeText(this, "Clicked on " + topDoctor.getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(IntroActivity.this, ScheduleActivity.class);
            intent.putExtra("DOCTOR_NAME", topDoctor.getName());
            startActivity(intent);
        });
    }

    // Sample data for Doctor Speciality
    private List<Speciality> getSpecialities() {
        List<Speciality> specialities = new ArrayList<>();
        specialities.add(new Speciality(R.drawable.cardio, "Cardiology"));
        specialities.add(new Speciality(R.drawable.orthope, "Orthopedics"));
        specialities.add(new Speciality(R.drawable.neuro, "Neurologist"));
        specialities.add(new Speciality(R.drawable.dentis, "Dental"));
        specialities.add(new Speciality(R.drawable.radiolo, "Radiologist"));
        return specialities;
    }

    // Sample data for Top Doctors
    private List<TopDoctor> getTopDoctors() {
        List<TopDoctor> topDoctors = new ArrayList<>();
        topDoctors.add(new TopDoctor(R.drawable.dr_david, "Dr. John Doe", "Cardiologist", 4.8, 9));
        topDoctors.add(new TopDoctor(R.drawable.dr_rich, "Dr. Jane Smith", "Orthopedic", 4.5, 8));
        topDoctors.add(new TopDoctor(R.drawable.dr_jessica, "Dr. Jessica", "Neurologist", 4.8, 7));
        topDoctors.add(new TopDoctor(R.drawable.dr_rich, "Dr. Richard Roe", "Dental", 4.5, 8));
        topDoctors.add(new TopDoctor(R.drawable.dr_sarah, "Dr. Sarah", "Radiologist", 4.5, 7));
        return topDoctors;
    }

}
