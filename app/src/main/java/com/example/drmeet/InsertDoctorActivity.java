package com.example.drmeet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drmeet.Database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertDoctorActivity extends AppCompatActivity {

    private EditText etDoctorName, etDoctorSpeciality;
    private RatingBar doctorRating;
    private ImageView doctorImage;
    private Button btnSelectImage, btnInsertDoctor;
    private Bitmap selectedBitmap;
    private DatabaseHelper databaseHelper;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_doctor);

        etDoctorName = findViewById(R.id.et_doctor_name);
        etDoctorSpeciality = findViewById(R.id.et_doctor_speciality);
        doctorRating = findViewById(R.id.doctor_rating);
        doctorImage = findViewById(R.id.doctor_image);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnInsertDoctor = findViewById(R.id.btn_insert_doctor);

        databaseHelper = new DatabaseHelper(this);

        btnSelectImage.setOnClickListener(v -> selectImage());
        btnInsertDoctor.setOnClickListener(v -> insertDoctor());
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                doctorImage.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    private void insertDoctor() {
        String name = etDoctorName.getText().toString().trim();
        String speciality = etDoctorSpeciality.getText().toString().trim();
        float rating = doctorRating.getRating();

        if (name.isEmpty() || speciality.isEmpty() || selectedBitmap == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        byte[] imageBytes = getBitmapAsByteArray(selectedBitmap);
        boolean isInserted = databaseHelper.insertDoctor(name, speciality, rating, imageBytes);

        if (isInserted) {
            Toast.makeText(this, "Doctor inserted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to insert doctor", Toast.LENGTH_SHORT).show();
        }
    }
}
