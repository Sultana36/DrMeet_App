package com.example.drmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drmeet.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        databaseHelper = new DatabaseHelper(this);

        setupSlotButton(R.id.btn_morning1, "10:00 To 10:30");
        setupSlotButton(R.id.btn_morning2, "10:30 To 11:00");
        setupSlotButton(R.id.btn_morning3, "11:00 To 11:30");
        setupSlotButton(R.id.btn_morning4, "11:30 To 12:00");
        setupSlotButton(R.id.btn_morning5, "12:30 To 01:00");
        setupSlotButton(R.id.btn_morning6, "01:00 To 01:30");
        setupSlotButton(R.id.btn_morning7, "01:30 To 02:00");
        setupSlotButton(R.id.btn_morning8, "02:00 To 02:30");
        setupSlotButton(R.id.btn_evening1, "06:00 To 06:30");
        setupSlotButton(R.id.btn_evening2, "06:30 To 07:00");
        setupSlotButton(R.id.btn_evening3, "07:00 To 07:30");
        setupSlotButton(R.id.btn_evening4, "07:30 To 08:00");
        setupSlotButton(R.id.btn_evening5, "08:00 To 08:30");
        setupSlotButton(R.id.btn_evening6, "08:30 To 09:00");
        setupSlotButton(R.id.btn_evening7, "09:00 To 09:30");
        setupSlotButton(R.id.btn_evening8, "09:30 To 10:00");
        setNextDayDate();
    }

    private void setupSlotButton(int buttonId, final String slotTime) {
        Button button = findViewById(buttonId);

        // Unique key for this exact button (e.g. "btn_morning4"), so buttons that
        // share the same time text no longer block each other
        final String slotKey = getResources().getResourceEntryName(buttonId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only CHECK availability here. The slot is locked later,
                // when the patient form is submitted in PaymentActivity.
                if (!databaseHelper.isTimeAvailable(slotKey)) {
                    Toast.makeText(ScheduleActivity.this, "This slot is not available anymore", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ScheduleActivity.this, PaymentActivity.class);
                    intent.putExtra("slotTime", slotTime);
                    intent.putExtra("slotKey", slotKey);
                    startActivity(intent);
                }
            }
        });
    }

    private void setNextDayDate() {
        TextView tvDate = findViewById(R.id.tv_date);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault());
        String nextDay = dateFormat.format(calendar.getTime());

        tvDate.setText("Available Slots for: " + nextDay);
    }
}