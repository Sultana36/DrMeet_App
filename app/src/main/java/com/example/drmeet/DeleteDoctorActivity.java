package com.example.drmeet;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drmeet.Database.DatabaseHelper;

public class DeleteDoctorActivity extends Activity {

    private EditText searchEditText;
    private Button searchButton, deleteButton;
    private ImageView imageView;
    private TextView nameTextView, professionTextView;
    private DatabaseHelper databaseHelper;
    private Doctor currentDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_doctor);

        searchEditText = findViewById(R.id.edit_text_search);
        searchButton = findViewById(R.id.button_search);
        imageView = findViewById(R.id.image_view_doctor);
        nameTextView = findViewById(R.id.text_view_name);
        professionTextView = findViewById(R.id.text_view_speciality);
        deleteButton = findViewById(R.id.button_delete);

        databaseHelper = new DatabaseHelper(this);

        searchButton.setOnClickListener(v -> searchDoctor());
        deleteButton.setOnClickListener(v -> deleteDoctor());
    }

    private void searchDoctor() {
        String name = searchEditText.getText().toString().trim();
        currentDoctor = databaseHelper.getDoctorByName(name);

        if (currentDoctor != null) {
            nameTextView.setText(currentDoctor.getName());
            professionTextView.setText(currentDoctor.getSpeciality());

            byte[] imageBytes = currentDoctor.getImage();
            if (imageBytes != null && imageBytes.length > 0) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
            } else {
                imageView.setImageResource(R.drawable.default_image);
            }
        } else {
            Toast.makeText(this, "Doctor not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteDoctor() {
        if (currentDoctor != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete " + currentDoctor.getName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        databaseHelper.deleteDoctor(currentDoctor.getId());
                        Toast.makeText(this, "Doctor deleted successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            Toast.makeText(this, "No doctor selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        searchEditText.setText("");
        nameTextView.setText("");
        professionTextView.setText("");
        imageView.setImageResource(R.drawable.mydelete);
    }
}