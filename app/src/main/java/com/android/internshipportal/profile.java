package com.android.internshipportal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class profile extends Fragment {

    TextView pname, pmobile, pdepartment, pemail;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;
    private onFragmentSelected listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Button editProfile = view.findViewById(R.id.editprofilebtn);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pname = view.findViewById(R.id.profileName);
        pdepartment = view.findViewById(R.id.profileDepartment);
        pmobile = view.findViewById(R.id.profileMobileNumber);
        pemail = view.findViewById(R.id.profileEmail);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                assert value != null;
                pname.setText(value.getString("name"));
                pdepartment.setText(value.getString("department"));
                pmobile.setText(value.getString("mobile"));
                pemail.setText(value.getString("email"));
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentSelected) {
            listener = (onFragmentSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");
        }

    }

    public interface onFragmentSelected {
        public void onButtonSelected();
    }


}