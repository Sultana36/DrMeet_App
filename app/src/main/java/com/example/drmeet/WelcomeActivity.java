package com.example.drmeet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drmeet.loginSignup.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Get reference to the Get Started button
        Button getStartedButton = findViewById(R.id.getStartedButton);

        // Set OnClickListener to navigate to the next activity
        getStartedButton.setOnClickListener(v -> {
            // Navigate to the next screen (e.g., LoginActivity)
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}