package com.android.internshipportal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

public class main_faculty extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_faculty, container, false);
        MaterialCardView profile, requests;

        profile = view.findViewById(R.id.profilebtn);
        profile.setOnClickListener(View -> startActivity(new Intent(requireActivity().getApplicationContext(), com.android.internshipportal.profile.class)));

        requests = view.findViewById(R.id.nocrqsts);
        requests.setOnClickListener(View -> startActivity(new Intent(requireActivity().getApplicationContext(), noc_requests.class)));

        return view;
    }
}