package com.android.internshipportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Objects;

public class noc_requests extends AppCompatActivity {

    FirebaseFirestore fstore;
    RecyclerView recyclerView;
    ArrayList<recycle_getter_setter> nocArrayList;
    nocAdapter nocAdapter;
    ProgressDialog progressDialog;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_noc_requests);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fstore = FirebaseFirestore.getInstance();
        nocArrayList = new ArrayList<>();
        nocAdapter = new nocAdapter(noc_requests.this, nocArrayList);

        recyclerView.setAdapter(nocAdapter);

        fetchData();

    }

    private void fetchData() {

        fstore.collection("Users").orderBy("formFilled", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {

            if (error != null) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.e("Firestore Error", error.getMessage());
                return;
            }

            for (DocumentChange dc : Objects.requireNonNull(value).getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    nocArrayList.add(dc.getDocument().toObject(recycle_getter_setter.class));
                }
                nocAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }

}