package com.android.internshipportal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class login extends AppCompatActivity {

    MaterialButton logBtn;
    MaterialTextView regLink, forgotPass;
    TextInputLayout loginEmail, loginPass;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CharSequence textFieldError=this.getResources().getString(R.string.field_empty_error);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_pass);
        logBtn =  findViewById(R.id.login);
        logBtn.setOnClickListener(View -> loginUser(textFieldError));

        regLink = findViewById(R.id.regilink);
        regLink.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, register.class);
            startActivity(intent);
        });

        forgotPass =  findViewById(R.id.forgotpassword);
        forgotPass.setOnClickListener(v -> {
            Intent intent = new Intent(login.this, forgotPassword.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.getString("isAdmin") != null) {
                    startActivity(new Intent(getApplicationContext(),admin_home.class));
                    finish();
                }

                if (documentSnapshot.getString("isStudent") != null) {
                    startActivity(new Intent(getApplicationContext(),student_home.class));
                    finish();
                }

                if (documentSnapshot.getString("isFaculty") != null) {
                    startActivity(new Intent(getApplicationContext(),faculty_home.class));
                    finish();
                }
            }).addOnFailureListener(e -> {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
            });
        }
    }

    private void loginUser(CharSequence textFieldError) {
        String email = Objects.requireNonNull(loginEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(loginPass.getEditText()).getText().toString();

        if (TextUtils.isEmpty(email)) {
            loginEmail.setError(textFieldError);
            loginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            loginPass.setError(textFieldError);
            loginPass.requestFocus();
        } else {

            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                Toast.makeText(login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                userAccessLevel(Objects.requireNonNull(authResult.getUser()).getUid());
            }).addOnFailureListener(e -> Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void userAccessLevel(String uid) {
        DocumentReference documentReference = fStore.collection("Users").document(uid);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {

            if (documentSnapshot.getString("isFaculty") != null) {
                startActivity(new Intent(getApplicationContext(),faculty_home.class));
                finish();
            } else if (documentSnapshot.getString("isAdmin") != null) {
                startActivity(new Intent(getApplicationContext(),admin_home.class));
                finish();
            } else if (documentSnapshot.getString("isStudent") != null) {
                startActivity(new Intent(getApplicationContext(),student_home.class));
                finish();
            }
        });
    }

}
