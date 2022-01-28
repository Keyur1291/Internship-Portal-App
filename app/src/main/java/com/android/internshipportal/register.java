package com.android.internshipportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button regbtn;
    TextView loglink;
    TextInputLayout regDepartment, regName, regMobile, regPass, regConf_pass, regEn_no, regEmail;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore fireStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        regName = findViewById(R.id.reg_name);
        regEn_no = findViewById(R.id.reg_en_no);
        regDepartment = (TextInputLayout) findViewById(R.id.dmenu);
        regMobile = findViewById(R.id.reg_mobile_no);
        regEmail= findViewById(R.id.reg_email);
        regPass = findViewById(R.id.reg_pasword);
        regConf_pass = findViewById(R.id.reg_conf_password);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.department);

        arrayList = new ArrayList<>();
        arrayList.add("Information Technology");
        arrayList.add("Computer Engineering");
        arrayList.add("Chemical Engineering");
        arrayList.add("Electrical Engineering");
        arrayList.add("Environmental Engineering");
        arrayList.add("Civil Engineering");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.department_item, arrayList);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);

        regbtn = (Button) findViewById(R.id.regibtn);
        regbtn.setOnClickListener(View -> {
           createUser();
        });

        loglink = (TextView) findViewById(R.id.loginlink);
        loglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }

    private void createUser() {
       String name = regName.getEditText().getText().toString();
        String enrollment = regEn_no.getEditText().getText().toString();
        String department = regDepartment.getEditText().getText().toString();
        String mobile = regMobile.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String password = regPass.getEditText().getText().toString();
        String confirmpassword = regConf_pass.getEditText().getText().toString();

        if (TextUtils.isEmpty(name)) {
            regName.setError("Field cannot be empty");
            regName.requestFocus();
        } else if (TextUtils.isEmpty(enrollment)) {
            regEn_no.setError("Field cannot be empty");
            regEn_no.requestFocus();
        } else if (TextUtils.isEmpty(department)) {
            regDepartment.setError("Field cannot be empty");
            regDepartment.requestFocus();
        } else if (TextUtils.isEmpty(mobile)) {
            regMobile.setError("Field cannot be empty");
            regMobile.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            regEmail.setError("Field cannot be empty");
            regEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            regPass.setError("Field cannot be empty");
            regPass.requestFocus();
        } else if (TextUtils.isEmpty(confirmpassword)) {
            regConf_pass.setError("Please confirm your password");
            regConf_pass.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        userID =mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fireStore.collection("Users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("enrollment", enrollment);
                        user.put("department", department);
                        user.put("mobile", mobile);
                        user.put("email", email);
                        user.put("password", password);
                        user.put("isStudent","1");

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "onSuccess: User profile is created for "+ userID);
                            }
                        });
                        startActivity(new Intent(register.this, login.class));
                    }else {
                        Toast.makeText(register.this, "Registration Error: " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}