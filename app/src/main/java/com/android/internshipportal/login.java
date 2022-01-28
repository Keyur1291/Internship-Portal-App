package com.android.internshipportal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
            startActivity(new Intent(login.this, navigation_drawer.class));
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

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(login.this, R.string.user_logged_in, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(login.this, navigation_drawer.class));
                } else {
                    Toast.makeText(login.this, R.string.login_error + Objects.requireNonNull(task.getException()).getMessage() , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
