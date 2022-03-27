package com.android.internshipportal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class noc_requests extends AppCompatActivity {

    FirebaseFirestore fstore;
    RecyclerView recyclerView;
    ArrayList<recycle_getter_setter> userArrayList;
    userAdapter userAdapter;
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
        userArrayList = new ArrayList<recycle_getter_setter>();
        userAdapter = new userAdapter(noc_requests.this, userArrayList);

        recyclerView.setAdapter(userAdapter);

        fetchData();

    }

    private void fetchData() {

        fstore.collection("Users").orderBy("formFilled", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore Error", error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        userArrayList.add(dc.getDocument().toObject(recycle_getter_setter.class));
                    }
                    userAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });

    }

}