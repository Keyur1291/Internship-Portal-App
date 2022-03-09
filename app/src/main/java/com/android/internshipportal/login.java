package com.android.internshipportal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class login extends AppCompatActivity {

    Button logBtn;
    TextView regLink;
    TextView forgotPass;
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
        logBtn.setOnClickListener(View -> {
            try{
                loginUser(textFieldError);
            }catch (Exception e){
                Toast.makeText(this,R.string.login_error +" "+e,Toast.LENGTH_SHORT).show();
            }
        });

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
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            startActivity(new Intent(login.this, admin_home.class));
            finish();
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

            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    userAccessLevel(authResult.getUser().getUid());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(login.this,"Login Error" + Objects.requireNonNull(e), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void userAccessLevel(String uid) {
        DocumentReference documentReference = fStore.collection("Users").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

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
            }
        });
    }

}
