package com.android.internshipportal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class internship_form extends AppCompatActivity {

    TextInputLayout cSubject ,cName, cAddress, cMobile, cEmail;
    MaterialButton apply;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internship_form);
        CharSequence fieldError = this.getApplicationContext().getText(R.string.field_empty_error);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        cSubject = findViewById(R.id.areaOfInterest);
        cName = findViewById(R.id.comp_name);
        cAddress = findViewById(R.id.comp_address);
        cMobile = findViewById(R.id.comp_mobile_no);
        cEmail = findViewById(R.id.comp_email);
        autoCompleteTextView = findViewById(R.id.area);

        arrayList = new ArrayList<>();
        arrayList.add("Web Development");
        arrayList.add("Application Development");
        arrayList.add("Internet Of Things");
        arrayList.add(".Net Programming");
        arrayList.add("React native");
        arrayList.add("Artificial Intelligence");
        arrayList.add("Python");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, arrayList);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);

        apply = findViewById(R.id.applyBtn);
        apply.setOnClickListener(v -> {
            applyInternship(fieldError);
        });



    }

    private void applyInternship(CharSequence fieldError) {
        String subject = Objects.requireNonNull(cSubject.getEditText()).getText().toString();
        String name = Objects.requireNonNull(cName.getEditText()).getText().toString();
        String address = Objects.requireNonNull(cAddress.getEditText()).getText().toString();
        String mobile = Objects.requireNonNull(cMobile.getEditText()).getText().toString();
        String email = Objects.requireNonNull(cEmail.getEditText()).getText().toString();

        if (TextUtils.isEmpty(subject)) {
            cSubject.setError(fieldError);
            cSubject.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            cName.setError(fieldError);
            cName.requestFocus();
        }else if (TextUtils.isEmpty(address)) {
            cAddress.setError(fieldError);
            cAddress.requestFocus();
        } else if (mobile.length() > 10) {
            cMobile.setError("Mobile number should not be longer than 10 digits");
            cMobile.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            cEmail.setError(fieldError);
            cEmail.requestFocus();
        } else {
            userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        }
    }
}