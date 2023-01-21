package com.android.internshipportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class company_details extends AppCompatActivity {

    MaterialTextView subject, cName, cAddress, cMobile, cEmail;
    MaterialToolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_company_details);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        subject = findViewById(R.id.subject);
        cName = findViewById(R.id.companyName);
        cAddress = findViewById(R.id.companyAddress);
        cMobile = findViewById(R.id.companyMobile);
        cEmail = findViewById(R.id.companyEmail);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                subject.setText(documentSnapshot.getString("subject"));
                cName.setText(documentSnapshot.getString("Cname"));
                cAddress.setText(documentSnapshot.getString("Caddress"));
                cMobile.setText(documentSnapshot.getString("Cmobile"));
                cEmail.setText(documentSnapshot.getString("Cemail"));
            }
        }).addOnFailureListener(e -> Toast.makeText(company_details.this, e.getMessage(), Toast.LENGTH_SHORT).show());

    }
}