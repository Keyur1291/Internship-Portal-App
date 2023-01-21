package com.android.internshipportal;

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

public class studentAdapter extends RecyclerView.Adapter<studentAdapter.myViewHolder> {

    final students_list studentsList;
    final ArrayList<recycle_getter_setter> userArrayList;
    final FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    public studentAdapter(students_list studentsList, ArrayList<recycle_getter_setter> userArrayList) {
        this.userArrayList = userArrayList;
        this.studentsList = studentsList;

    }

    @NonNull
    @Override
    public studentAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(studentsList).inflate(R.layout.students_list_item, parent,false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull studentAdapter.myViewHolder holder, int position) {

        recycle_getter_setter recycle_getter_setter = userArrayList.get(position);

        holder.name.setText(recycle_getter_setter.name);
        holder.enrollment.setText(recycle_getter_setter.enrollment);
        holder.department.setText(recycle_getter_setter.department);
        holder.mobile.setText(recycle_getter_setter.mobile);
        holder.email.setText(recycle_getter_setter.email);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        final MaterialTextView name;
        final MaterialTextView enrollment;
        final MaterialTextView department;
        final MaterialTextView mobile;
        final MaterialTextView email;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.editbtn).setOnClickListener(View -> {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(studentsList, R.style.ThemeOverlay_App_MaterialAlertDialog);
                    dialogBuilder.setTitle("Edit Profile");
                    dialogBuilder.setIcon(R.drawable.ic_baseline_edit_24);
                    dialogBuilder.setMessage("Are you sure want to edit this profile?");
                    dialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
                        updateData(getAdapterPosition());
                        notifyDataSetChanged();
                    }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
                    dialogBuilder.show();
            });

            itemView.findViewById(R.id.deletbtn).setOnClickListener(View -> {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(studentsList, R.style.ThemeOverlay_App_MaterialAlertDialog);
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
            enrollment = itemView.findViewById(R.id.lEnno);
            department = itemView.findViewById(R.id.lDdept);
            mobile = itemView.findViewById(R.id.lMobile);
            email = itemView.findViewById(R.id.lEmail);
        }
    }

    public void updateData(int position) {

        recycle_getter_setter item = userArrayList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uid", item.getid());
        bundle.putString("uname", item.getName());
        bundle.putString("uenrollment", item.getEnrollment());
        bundle.putString("udepartment", item.getDepartment());
        bundle.putString("umobile", item.getMobile());
        bundle.putString("uemail", item.getEmail());
        Intent intent = new Intent(studentsList, edit_student.class);
        intent.putExtras(bundle);
        studentsList.startActivity(intent);
    }

    public void deleteData(int position) {
        recycle_getter_setter item = userArrayList.get(position);
        fstore.collection("Users").document(item.getid()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                removed(position);
                Toast.makeText(studentsList, "User Deleted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(studentsList, "Error:" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removed(int position) {
        userArrayList.remove(position);
        notifyItemRemoved(position);
        studentsList.fetchData();
    }

}
