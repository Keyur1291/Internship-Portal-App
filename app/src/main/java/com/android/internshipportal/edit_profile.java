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

public class edit_profile extends AppCompatActivity {

    MaterialButton save;
    MaterialToolbar toolbar;
    TextInputLayout regDepartment, regName, regMobile, regEmail;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_edit_profile);

        CharSequence fieldError = this.getApplicationContext().getText(R.string.field_empty_error);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        fStore = FirebaseFirestore.getInstance();
        regName = findViewById(R.id.reg_name);
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

        save = findViewById(R.id.saveprofilebtn);
        save.setOnClickListener(v -> editUser(fieldError));

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Objects.requireNonNull(regName.getEditText()).setText(documentSnapshot.getString("name"));
                Objects.requireNonNull(regDepartment.getEditText()).setText(documentSnapshot.getString("department"));
                Objects.requireNonNull(regMobile.getEditText()).setText(documentSnapshot.getString("mobile"));
                Objects.requireNonNull(regEmail.getEditText()).setText(documentSnapshot.getString("email"));
            }
        }).addOnFailureListener(e -> Toast.makeText(edit_profile.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void editUser(CharSequence fieldError) {

        String name = Objects.requireNonNull(regName.getEditText()).getText().toString();
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
        } else if (TextUtils.isEmpty(email)) {
            regEmail.setError(fieldError);
            regEmail.requestFocus();
        } else {
            DocumentReference documentReference = fStore.collection("Users").document(userID);
            Map<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("department", department);
            user.put("mobile", mobile);
            user.put("email", email);

            documentReference.set(user, SetOptions.merge()).addOnSuccessListener(unused -> {
                Toast.makeText(edit_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(edit_profile.this, profile.class));
                finish();
            }).addOnFailureListener(e -> Toast.makeText(edit_profile.this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show());

        }

    }
}