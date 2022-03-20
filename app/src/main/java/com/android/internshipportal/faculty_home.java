package com.android.internshipportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class faculty_home extends AppCompatActivity {

    MaterialButton logout, nocBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);

        mAuth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(View -> {
            mAuth.signOut();
            Toast.makeText(faculty_home.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(faculty_home.this, login.class));
        });

        nocBtn = findViewById(R.id.nocRequests);
        nocBtn.setOnClickListener(v -> {
            startActivity(new Intent(faculty_home.this, noc_requests.class));
        });
    }
}