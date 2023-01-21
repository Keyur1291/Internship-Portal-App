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

public class main_student extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_student, container, false);
        MaterialCardView profile, company, prevcompany, internship;

        profile = view.findViewById(R.id.profilebtn);
        profile.setOnClickListener(View -> startActivity(new Intent(requireActivity().getApplicationContext(), com.android.internshipportal.profile.class)));

        company = view.findViewById(R.id.companydtls);
        company.setOnClickListener(View -> startActivity(new Intent(requireActivity().getApplicationContext(), company_details.class)));

        prevcompany = view.findViewById(R.id.prevcompanies);
        prevcompany.setOnClickListener(View -> startActivity(new Intent(requireActivity().getApplicationContext(), company_list.class)));

        internship = view.findViewById(R.id.applyinternship);
        internship.setOnClickListener(View -> startActivity(new Intent(requireActivity().getApplicationContext(), internship_form.class)));

        return view;
    }
}