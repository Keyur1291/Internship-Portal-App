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

public class company_list extends AppCompatActivity {

    FirebaseFirestore fstore;
    RecyclerView recyclerView;
    ArrayList<recycle_getter_setter> companyArrayList;
    companyAdapter companyAdapter;
    ProgressDialog progressDialog;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_company_list);

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
        companyArrayList = new ArrayList<>();
        companyAdapter = new companyAdapter(company_list.this, companyArrayList);

        recyclerView.setAdapter(companyAdapter);

        fetchData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchData() {

        fstore.collection("Companies").orderBy("Cname", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {

            if (error != null) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.e("Fire store Error", error.getMessage());
                return;
            }

            assert value != null;
            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    companyArrayList.add(dc.getDocument().toObject(recycle_getter_setter.class));
                }
                companyAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }
}