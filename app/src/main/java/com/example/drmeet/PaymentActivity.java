package com.example.drmeet;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drmeet.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class PaymentActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, phoneEditText;
    private Spinner itemSpinner;
    private String name, email, phone, slotTime, slotKey;
    private String appointmentType = "Select Appointment Type";
    private Button submit;
    private Pattern namePattern = Pattern.compile("[a-zA-Z._ ]+");
    private Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+$");

    private LinearLayout inputLayout, outputLayout;
    private TextView outputText, confirmationMessage;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.num);
        itemSpinner = findViewById(R.id.item_spinner);
        submit = findViewById(R.id.submit);
        inputLayout = findViewById(R.id.inputLayout);
        outputLayout = findViewById(R.id.outputLayout);
        outputText = findViewById(R.id.outputText);
        confirmationMessage = findViewById(R.id.confirmationMessage);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve the slot info from ScheduleActivity
        slotTime = getIntent().getStringExtra("slotTime");
        slotKey = getIntent().getStringExtra("slotKey");
        if (slotTime == null || slotKey == null) {
            Toast.makeText(this, "No time slot selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Spinner Items - Appointment Types
        String[] appointmentTypes = {"Select Appointment Type", "General Check-up", "Follow-up Visit", "Specialist Consultation", "Lab Test", "Vaccination", "Health Screening"};
        itemSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, appointmentTypes));

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appointmentType = itemSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                appointmentType = "Select Appointment Type";
            }
        });

        submit.setOnClickListener(v -> {
            name = nameEditText.getText().toString().trim();
            email = emailEditText.getText().toString().trim();
            phone = phoneEditText.getText().toString().trim();

            // Validation
            if (name.isEmpty()) {
                nameEditText.setError("Please enter your name");
                nameEditText.requestFocus();
            } else if (!namePattern.matcher(name).matches()) {
                nameEditText.setError("Name can only contain alphabets");
                nameEditText.requestFocus();
            } else if (email.isEmpty()) {
                emailEditText.setError("Please enter your email");
                emailEditText.requestFocus();
            } else if (!emailPattern.matcher(email).matches()) {
                emailEditText.setError("Invalid email format");
                emailEditText.requestFocus();
            } else if (phone.isEmpty()) {
                phoneEditText.setError("Please enter your phone number");
                phoneEditText.requestFocus();
            } else if (phone.length() != 11) {
                phoneEditText.setError("Phone number must be 11 digits");
                phoneEditText.requestFocus();
            } else if (appointmentType.equals("Select Appointment Type")) {
                Toast.makeText(PaymentActivity.this, "Please select an appointment type", Toast.LENGTH_SHORT).show();
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                String nextDay = dateFormat.format(calendar.getTime());
                String time = nextDay + " : " + slotTime;

                // The patient is saved AND the slot is locked only here, after a valid form
                boolean booked = databaseHelper.bookAppointment(name, email, phone, appointmentType, time, slotKey);
                if (!booked) {
                    Toast.makeText(PaymentActivity.this,
                            "Sorry, this slot was just taken. Please choose another one.",
                            Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                // Displaying Information
                inputLayout.setVisibility(View.GONE);
                outputLayout.setVisibility(View.VISIBLE);
                String result = "Name: " + name + "\nEmail: " + email + "\nMobile Number: " + phone + "\nAppointment Type: " + appointmentType;
                outputText.setText(result);
            }
        });
    }
}