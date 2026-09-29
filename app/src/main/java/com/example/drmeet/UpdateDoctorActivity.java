package com.example.drmeet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class UpdateDoctorActivity extends AppCompatActivity {

    private EditText etSearch, etName, etSpeciality;
    private RatingBar ratingBar;
    private ImageView imageViewDoctor;
    private Button btnSelectImage, btnUpdate, btnSearch;
    private Bitmap selectedBitmap;
    private DatabaseHelper databaseHelper;
    private Doctor currentDoctor;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);

        etSearch = findViewById(R.id.edit_text_search);
        etName = findViewById(R.id.edit_text_name);
        etSpeciality = findViewById(R.id.edit_text_speciality);
        ratingBar = findViewById(R.id.rating_bar);
        imageViewDoctor = findViewById(R.id.image_view_doctor);
        btnSelectImage = findViewById(R.id.button_select_image);
        btnUpdate = findViewById(R.id.button_update);
        btnSearch = findViewById(R.id.button_search);

        databaseHelper = new DatabaseHelper(this);

        btnSearch.setOnClickListener(v -> searchDoctor());
        btnSelectImage.setOnClickListener(v -> selectImage());
        btnUpdate.setOnClickListener(v -> updateDoctor());
    }

    private void searchDoctor() {
        String name = etSearch.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name to search", Toast.LENGTH_SHORT).show();
            return;
        }

        currentDoctor = databaseHelper.getDoctorByName(name);
        if (currentDoctor != null) {
            etName.setText(currentDoctor.getName());
            etSpeciality.setText(currentDoctor.getSpeciality());
            ratingBar.setRating(currentDoctor.getRating());
            imageViewDoctor.setImageBitmap(BitmapFactory.decodeByteArray(currentDoctor.getImage(), 0, currentDoctor.getImage().length));
        } else {
            Toast.makeText(this, "Doctor not found", Toast.LENGTH_SHORT).show();
        }
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
                imageViewDoctor.setImageBitmap(selectedBitmap);
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

    private void updateDoctor() {
        if (currentDoctor == null) {
            Toast.makeText(this, "Please search for a doctor first", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = etName.getText().toString().trim();
        String speciality = etSpeciality.getText().toString().trim();
        float rating = ratingBar.getRating();

        byte[] imageBytes = (selectedBitmap != null) ? getBitmapAsByteArray(selectedBitmap) : currentDoctor.getImage();

        boolean isUpdated = databaseHelper.updateDoctor(currentDoctor.getId(), name, speciality, rating, imageBytes);

        if (isUpdated) {
            Toast.makeText(this, "Doctor updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update doctor", Toast.LENGTH_SHORT).show();
        }
    }
}
