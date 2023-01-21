package com.android.internshipportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class add_student extends AppCompatActivity {

    public static final String TAG = "TAG";
    MaterialButton regBtn;
    MaterialToolbar toolbar;
    TextInputLayout regDepartment, regName, regMobile, regPass, regEn_no, regEmail;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore fireStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_add_student);

        CharSequence fieldError = this.getApplicationContext().getText(R.string.field_empty_error);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        regName = findViewById(R.id.reg_name);
        regEn_no = findViewById(R.id.reg_en_no);
        regDepartment = findViewById(R.id.dmenu);
        regMobile = findViewById(R.id.reg_mobile_no);
        regEmail = findViewById(R.id.reg_email);
        regPass = findViewById(R.id.reg_pasword);
        autoCompleteTextView = findViewById(R.id.department);

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

        regBtn = findViewById(R.id.regibtn);
        regBtn.setOnClickListener(v -> createUser(fieldError));

    }

    private void createUser(CharSequence fieldError) {
        String name = Objects.requireNonNull(regName.getEditText()).getText().toString();
        String enrollment = Objects.requireNonNull(regEn_no.getEditText()).getText().toString();
        String department = Objects.requireNonNull(regDepartment.getEditText()).getText().toString();
        String mobile = Objects.requireNonNull(regMobile.getEditText()).getText().toString();
        String email = Objects.requireNonNull(regEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(regPass.getEditText()).getText().toString();

        if (TextUtils.isEmpty(name)) {
            regName.setError(fieldError);
            regName.requestFocus();
        } else if (TextUtils.isEmpty(enrollment)) {
            regEn_no.setError(fieldError);
            regEn_no.requestFocus();
        } else if (enrollment.length() > 15) {
            regEn_no.setError("Enrollment number should not be longer than 15 digits");
            regEn_no.requestFocus();
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
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(add_student.this, "Student Added successfully", Toast.LENGTH_SHORT).show();
                    userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    DocumentReference documentReference = fireStore.collection("Users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("id", userID);
                    user.put("name", name);
                    user.put("enrollment", enrollment);
                    user.put("department", department);
                    user.put("mobile", mobile);
                    user.put("email", email);
                    user.put("password", password);
                    user.put("isStudent", "1");

                    documentReference.set(user).addOnSuccessListener(unused -> Log.d(TAG, "Student" + name + "Added Successfully"));
                    startActivity(new Intent(add_student.this, admin_home.class));
                    finish();
                } else {
                    Toast.makeText(add_student.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}