package com.android.internshipportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class edit_student extends AppCompatActivity {

    MaterialButton save;
    MaterialToolbar toolbar;
    TextInputLayout regDepartment, regEn_no, regName, regMobile, regEmail;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;
    String uname, uenrollment, uid, udepartment, umobile, uemail;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_edit_student);

        CharSequence fieldError = this.getApplicationContext().getText(R.string.field_empty_error);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        fStore = FirebaseFirestore.getInstance();
        regName = findViewById(R.id.reg_name);
        regEn_no = findViewById(R.id.reg_en_no);
        regDepartment = findViewById(R.id.dmenu);
        regMobile = findViewById(R.id.reg_mobile_no);
        regEmail = findViewById(R.id.reg_email);
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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uid = bundle.getString("uid");
            uname = bundle.getString("uname");
            uenrollment = bundle.getString("uenrollment");
            udepartment = bundle.getString("udepartment");
            umobile = bundle.getString("umobile");
            uemail = bundle.getString("uemail");
            Objects.requireNonNull(regName.getEditText()).setText(uname);
            Objects.requireNonNull(regEn_no.getEditText()).setText(uenrollment);
            Objects.requireNonNull(regDepartment.getEditText()).setText(udepartment);
            Objects.requireNonNull(regMobile.getEditText()).setText(umobile);
            Objects.requireNonNull(regEmail.getEditText()).setText(uemail);
        }

        save = findViewById(R.id.saveprofilebtn);
        save.setOnClickListener(View -> {
            Bundle bundle1 = getIntent().getExtras();
            if (bundle1 != null) {
                String id = uid;
                update(id, fieldError);
            } else {
                editUser(fieldError);
            }
        });
    }

    private void update(String id, CharSequence fieldError) {

        String name = Objects.requireNonNull(regName.getEditText()).getText().toString();
        String enrollment = Objects.requireNonNull(regEn_no.getEditText()).getText().toString();
        String department = Objects.requireNonNull(regDepartment.getEditText()).getText().toString();
        String mobile = Objects.requireNonNull(regMobile.getEditText()).getText().toString();
        String email = Objects.requireNonNull(regEmail.getEditText()).getText().toString();

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
        } else if (TextUtils.isEmpty(enrollment)) {
            regEn_no.setError(fieldError);
            regEn_no.requestFocus();
        } else if (enrollment.length() > 15) {
            regEn_no.setError("Enrollment number should not be longer than 15 digits");
            regEn_no.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            regEmail.setError(fieldError);
            regEmail.requestFocus();
        } else {

            fStore.collection("Users").document(id).update(
                    "name", name,
                    "enrollment", enrollment,
                    "department", department,
                    "mobile", mobile,
                    "email", email).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(edit_student.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(edit_student.this, students_list.class));
                            finish();
                        } else {
                            Toast.makeText(edit_student.this, "Error:" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void editUser(CharSequence fieldError) {

        String name = Objects.requireNonNull(regName.getEditText()).getText().toString();
        String enrollment = Objects.requireNonNull(regEn_no.getEditText()).getText().toString();
        String department = Objects.requireNonNull(regDepartment.getEditText()).getText().toString();
        String mobile = Objects.requireNonNull(regMobile.getEditText()).getText().toString();
        String email = Objects.requireNonNull(regEmail.getEditText()).getText().toString();

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
        } else if (TextUtils.isEmpty(enrollment)) {
            regEn_no.setError(fieldError);
            regEn_no.requestFocus();
        } else if (enrollment.length() > 15) {
            regEn_no.setError("Enrollment number should not be longer than 15 digits");
            regEn_no.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            regEmail.setError(fieldError);
            regEmail.requestFocus();
        } else {
            DocumentReference documentReference = fStore.collection("Users").document(userID);
            Map<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("enrollment", enrollment);
            user.put("department", department);
            user.put("mobile", mobile);
            user.put("email", email);

            documentReference.set(user, SetOptions.merge()).addOnSuccessListener(unused -> {
                Toast.makeText(edit_student.this, "Student Profile Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(edit_student.this, admin_home.class));
                finish();
            }).addOnFailureListener(e -> Toast.makeText(edit_student.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show());

        }

    }

}