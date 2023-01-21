package com.android.internshipportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class facultyAdapter extends RecyclerView.Adapter<facultyAdapter.myViewHolder> {

    final faculty_list facultyList;
    final ArrayList<recycle_getter_setter> userArrayList;
    final FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    public facultyAdapter(faculty_list facultyList, ArrayList<recycle_getter_setter> userArrayList) {
        this.facultyList = facultyList;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public facultyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(facultyList).inflate(R.layout.faculty_list_item, parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        recycle_getter_setter recycle_getter_setter = userArrayList.get(position);

        holder.name.setText(recycle_getter_setter.name);
        holder.department.setText(recycle_getter_setter.department);
        holder.mobile.setText(recycle_getter_setter.mobile);
        holder.email.setText(recycle_getter_setter.email);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    protected class myViewHolder extends RecyclerView.ViewHolder {

        final MaterialTextView name;
        final MaterialTextView department;
        final MaterialTextView mobile;
        final MaterialTextView email;

        @SuppressLint("NotifyDataSetChanged")
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.Feditbtn).setOnClickListener(View -> {
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(facultyList, R.style.ThemeOverlay_App_MaterialAlertDialog);
                dialogBuilder.setTitle("Edit Profile");
                dialogBuilder.setIcon(R.drawable.ic_baseline_edit_24);
                dialogBuilder.setMessage("Are you sure want to edit this profile?");
                dialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    updateDataf(getAdapterPosition());
                    notifyDataSetChanged();
                }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                dialogBuilder.show();
            });

            itemView.findViewById(R.id.Fdeletbtn).setOnClickListener(View -> {
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(facultyList, R.style.ThemeOverlay_App_MaterialAlertDialog);
                dialogBuilder.setTitle("Delete Profile");
                dialogBuilder.setIcon(R.drawable.ic_baseline_delete_forever_24);
                dialogBuilder.setMessage("Are you sure want to delete this profile?");
                dialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    deleteData(getAdapterPosition());
                    notifyDataSetChanged();
                }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                dialogBuilder.show();
            });

            name = itemView.findViewById(R.id.lName);
            department = itemView.findViewById(R.id.lDdept);
            mobile = itemView.findViewById(R.id.lMobile);
            email = itemView.findViewById(R.id.lEmail);
        }
    }

    public void updateDataf(int position) {

        recycle_getter_setter item = userArrayList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uid", item.getid());
        bundle.putString("uname", item.getName());
        bundle.putString("udepartment", item.getDepartment());
        bundle.putString("umobile", item.getMobile());
        bundle.putString("uemail", item.getEmail());
        Intent intent = new Intent(facultyList, edit_faculty.class);
        intent.putExtras(bundle);
        facultyList.startActivity(intent);

    }

    public void deleteData(int position) {
        recycle_getter_setter item = userArrayList.get(position);
        fstore.collection("Users").document(item.getid()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                removed(position);
                Toast.makeText(facultyList, "User Deleted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(facultyList, "Error:" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removed(int position) {
        userArrayList.remove(position);
        notifyItemRemoved(position);
        facultyList.fetchData();
    }

}
