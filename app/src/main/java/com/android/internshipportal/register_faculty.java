package com.android.internshipportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class register_faculty extends AppCompatActivity {

    public static final String TAG = "TAG";
    MaterialButton regBtn;
    MaterialTextView logLink, regStudent;
    TextInputLayout regDepartment, regName, regMobile, regPass, regConf_pass, regEmail;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore fireStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_faculty);
        CharSequence fieldError=this.getApplicationContext().getText(R.string.field_empty_error);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        regName = findViewById(R.id.reg_namef);
        regDepartment = findViewById(R.id.dmenuf);
        regMobile = findViewById(R.id.reg_mobile_nof);
        regEmail = findViewById(R.id.reg_emailf);
        regPass = findViewById(R.id.reg_paswordf);
        regConf_pass = findViewById(R.id.reg_conf_passwordf);
        autoCompleteTextView = findViewById(R.id.departmentf);

        arrayList = new ArrayList<>();
        arrayList.add("Information Technology");
        arrayList.add("Computer Engineering");
        arrayList.add("Chemical Engineering");
        arrayList.add("Electrical Engineering");
        arrayList.add("Environmental Engineering");
        arrayList.add("Civil Engineering");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, arrayList);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);

        regBtn = findViewById(R.id.regibtnf);
        regBtn.setOnClickListener(v -> createUser(fieldError));

        logLink =  findViewById(R.id.loginlinkf);
        logLink.setOnClickListener(v -> {
            Intent intent = new Intent(register_faculty.this, login.class);
            startActivity(intent);
            finish();
        });

        regStudent = findViewById(R.id.studentlink);
        regStudent.setOnClickListener(v -> {
            Intent intent = new Intent(register_faculty.this, register.class);
            startActivity(intent);
            finish();
        });
    }

    private void createUser(CharSequence fieldError) {
        String name = Objects.requireNonNull(regName.getEditText()).getText().toString();
        String department = Objects.requireNonNull(regDepartment.getEditText()).getText().toString();
        String mobile = Objects.requireNonNull(regMobile.getEditText()).getText().toString();
        String email = Objects.requireNonNull(regEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(regPass.getEditText()).getText().toString();
        String confirmPassword = Objects.requireNonNull(regConf_pass.getEditText()).getText().toString();

        if (TextUtils.isEmpty(name)) {
            regName.setError(fieldError);
            regName.requestFocus();
        } else if (TextUtils.isEmpty(department)) {
            regDepartment.setError(fieldError);
            regDepartment.requestFocus();
        } else if (TextUtils.isEmpty(mobile)) {
            regMobile.setError(fieldError);
            regMobile.requestFocus();
        } else if (mobile.length() > 10) {
            regMobile.setError("Mobile number should not be longer than 10 digits");
            regMobile.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            regEmail.setError(fieldError);
            regEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            regPass.setError(fieldError);
            regPass.requestFocus();
        } else if (password.length() < 6) {
            regPass.setError("Password should be more longer");
            regPass.requestFocus();
        } else if (TextUtils.isEmpty(confirmPassword)) {
            regConf_pass.setError("Please confirm your password");
            regConf_pass.requestFocus();
        }
        else if (!confirmPassword.equals(password)) {
            regConf_pass.setError("Confirm password does not match to password");
            regConf_pass.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(register_faculty.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    DocumentReference documentReference = fireStore.collection("Users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("department", department);
                    user.put("mobile", mobile);
                    user.put("email", email);
                    user.put("password", password);
                    user.put("isFaculty", "1");

                    documentReference.set(user).addOnSuccessListener(unused -> Log.d(TAG, "onSuccess: User profile is created for " + name));
                    startActivity(new Intent(register_faculty.this, login.class));
                    finish();
                } else {
                    Toast.makeText(register_faculty.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}