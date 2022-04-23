package com.android.internshipportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class admin_home extends AppCompatActivity {

    MaterialToolbar toolbar;
    MaterialButton logout;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    MaterialCardView users, faculties , company, addStudent, addFaculty, addcompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_admin_home);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        logout = findViewById(R.id.logOut);
        logout.setOnClickListener(View -> {
            mAuth.signOut();
            Toast.makeText(admin_home.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(admin_home.this, login.class));
            finish();
        });

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        users = findViewById(R.id.usersbtn);
        users.setOnClickListener(View -> {
            startActivity(new Intent(admin_home.this, students_list.class));
        });

        faculties = findViewById(R.id.fcltysbtn);
        faculties.setOnClickListener(View -> {
            startActivity(new Intent(admin_home.this, faculty_list.class));
        });

        company = findViewById(R.id.companybtn);
        company.setOnClickListener(View -> {
            startActivity(new Intent(admin_home.this, company_list.class));
        });

        addStudent = findViewById(R.id.addstudent);
        addStudent.setOnClickListener(View -> {
            startActivity(new Intent(admin_home.this, add_student.class));
        });

        addFaculty = findViewById(R.id.addfaculty);
        addFaculty.setOnClickListener(View -> {
            startActivity(new Intent(admin_home.this, add_faculty.class));
        });

        addcompany = findViewById(R.id.addcompany);
        addcompany.setOnClickListener(View -> {
            startActivity(new Intent(admin_home.this, add_company.class));
        });

    }
}