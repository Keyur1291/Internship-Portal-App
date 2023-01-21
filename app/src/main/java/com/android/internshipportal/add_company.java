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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class add_company extends AppCompatActivity {

    TextInputLayout cSubject ,cName, cAddress, cMobile, cEmail;
    public static final String TAG = "TAG";
    MaterialButton apply;
    MaterialToolbar toolbar;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_add_company);

        CharSequence fieldError = this.getApplicationContext().getText(R.string.field_empty_error);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
        apply.setOnClickListener(v -> applyInternship(fieldError));

    }

    private void applyInternship(CharSequence fieldError) {

        String subject = Objects.requireNonNull(cSubject.getEditText()).getText().toString();
        String Cname = Objects.requireNonNull(cName.getEditText()).getText().toString();
        String Caddress = Objects.requireNonNull(cAddress.getEditText()).getText().toString();
        String Cmobile = Objects.requireNonNull(cMobile.getEditText()).getText().toString();
        String Cemail = Objects.requireNonNull(cEmail.getEditText()).getText().toString();

        if (TextUtils.isEmpty(subject)) {
            cSubject.setError(fieldError);
            cSubject.requestFocus();
        } else if (TextUtils.isEmpty(Cname)) {
            cName.setError(fieldError);
            cName.requestFocus();
        }else if (TextUtils.isEmpty(Caddress)) {
            cAddress.setError(fieldError);
            cAddress.requestFocus();
        } else if (Cmobile.length() > 10) {
            cMobile.setError("Mobile number should not be longer than 10 digits");
            cMobile.requestFocus();
        } else if (TextUtils.isEmpty(Cemail)) {
            cEmail.setError(fieldError);
            cEmail.requestFocus();
        } else {

            Map<String, Object> company = new HashMap<>();
            company.put("subject", subject);
            company.put("Cname", Cname);
            company.put("Caddress", Caddress);
            company.put("Cmobile", Cmobile);
            company.put("Cemail", Cemail);
            fStore.collection("Companies").add(company).addOnCompleteListener(task -> {
                Toast.makeText(add_company.this, "Company Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(add_company.this, admin_home.class));
                finish();
            }).addOnFailureListener(e -> Toast.makeText(add_company.this, "Error;" + e.getMessage(), Toast.LENGTH_SHORT).show());
        }

    }
}