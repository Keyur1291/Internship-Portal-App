package com.android.internshipportal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class company_details extends Fragment {

    MaterialTextView subject, cName, cAddress, cMobile, cEmail;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subject = view.findViewById(R.id.subject);
        cName = view.findViewById(R.id.companyName);
        cAddress = view.findViewById(R.id.companyAddress);
        cMobile = view.findViewById(R.id.companyMobile);
        cEmail = view.findViewById(R.id.companyEmail);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                subject.setText(value.getString("subject"));
                cName.setText(value.getString("Cname"));
                cAddress.setText(value.getString("Caddress"));
                cMobile.setText(value.getString("Cmobile"));
                cEmail.setText(value.getString("Cemail"));
            }
        });

    }

}