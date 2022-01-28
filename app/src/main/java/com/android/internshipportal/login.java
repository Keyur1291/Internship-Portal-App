package com.android.internshipportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    Button logbtn;
    TextView reglink;
    TextView forgotpass;
    TextInputLayout loginEmail, loginPass;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_pass);
        logbtn = (Button) findViewById(R.id.login);
        logbtn.setOnClickListener(View -> {
            loginUser();
        });

        reglink = (TextView) findViewById(R.id.regilink);
        reglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });

        forgotpass = (TextView) findViewById(R.id.forgotpassword);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, forgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email = loginEmail.getEditText().getText().toString();
        String password = loginPass.getEditText().getText().toString();

         if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Field cannot be empty");
            loginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            loginPass.setError("Field cannot be empty");
            loginPass.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this, navigation_drawer.class));
                    } else {
                        Toast.makeText(login.this, "Login Error: " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
