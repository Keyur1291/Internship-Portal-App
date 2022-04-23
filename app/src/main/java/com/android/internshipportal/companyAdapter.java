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

public class companyAdapter extends RecyclerView.Adapter<companyAdapter.myViewHolder> {

    Context context;
    ArrayList<recycle_getter_setter> companyArrayList;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    public companyAdapter(Context context, ArrayList<recycle_getter_setter> companyArrayList) {
        this.context = context;
        this.companyArrayList = companyArrayList;
    }

    @NonNull
    @Override
    public companyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.company_list_item, parent,false);

        return new companyAdapter.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull companyAdapter.myViewHolder holder, int position) {

        recycle_getter_setter recycle_getter_setter = companyArrayList.get(position);
        holder.Cname.setText(recycle_getter_setter.Cname);
        holder.subject.setText(recycle_getter_setter.subject);
        holder.Caddress.setText(recycle_getter_setter.Caddress);
        holder.Cmobile.setText(recycle_getter_setter.Cmobile);
        holder.Cemail.setText(recycle_getter_setter.Cemail);
    }

    @Override
    public int getItemCount() {
        return companyArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView Cname, subject, Caddress, Cmobile, Cemail;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Cname = itemView.findViewById(R.id.cName);
            subject = itemView.findViewById(R.id.cAoi);
            Caddress = itemView.findViewById(R.id.cAddress);
            Cmobile = itemView.findViewById(R.id.cMobile);
            Cemail = itemView.findViewById(R.id.cEmail);
        }
    }

    public void updateDatac(int position) {

        recycle_getter_setter item = companyArrayList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uid", item.getid());
        bundle.putString("uname", item.getName());
        bundle.putString("uenrollment", item.getEnrollment());
        bundle.putString("udepartment", item.getDepartment());
        bundle.putString("umobile", item.getMobile());
        bundle.putString("uemail", item.getEmail());
        Intent intent = new Intent(context, add_company.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void deleteData(int position) {
        recycle_getter_setter item = companyArrayList.get(position);
        fstore.collection("Companies").document(item.getid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Company Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
