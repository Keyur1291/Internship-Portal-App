package com.android.internshipportal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class facultyAdapter extends RecyclerView.Adapter<facultyAdapter.myViewHolder> {

    Context context;
    ArrayList<recycle_getter_setter> userArrayList;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    public facultyAdapter(Context context, ArrayList<recycle_getter_setter> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public facultyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.faculty_list_item, parent,false);
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

    protected static class myViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView name, department, mobile, email;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
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
        Intent intent = new Intent(context, edit_faculty.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    public void deleteData(int position) {
        recycle_getter_setter item = userArrayList.get(position);
        fstore.collection("Users").document(item.getid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "User Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
