package com.android.internshipportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Objects;

public class faculty_list extends AppCompatActivity {

    FirebaseFirestore fstore;
    RecyclerView recyclerView;
    ArrayList<recycle_getter_setter> userArrayList;
    facultyAdapter facultyAdapter;
    ProgressDialog progressDialog;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_faculty_list);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fstore = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        facultyAdapter = new facultyAdapter(faculty_list.this, userArrayList);

        recyclerView.setAdapter(facultyAdapter);

        fetchData();

    }

    @SuppressLint("NotifyDataSetChanged")
    public void fetchData() {
        fstore.collection("Users").orderBy("isFaculty", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {

            if (error != null) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.e("Firestore Error", error.getMessage());
                return;
            }

            for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    userArrayList.add(dc.getDocument().toObject(recycle_getter_setter.class));
                }
                facultyAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }

}