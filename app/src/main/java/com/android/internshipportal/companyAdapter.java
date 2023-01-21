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

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class companyAdapter extends RecyclerView.Adapter<companyAdapter.myViewHolder> {

    final Context context;
    final ArrayList<recycle_getter_setter> companyArrayList;
    final FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    public companyAdapter(Context context, ArrayList<recycle_getter_setter> companyArrayList) {
        this.context = context;
        this.companyArrayList = companyArrayList;
    }

    @NonNull
    @Override
    public companyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.company_list_item, parent,false);

        return new myViewHolder(v);
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

    public static class myViewHolder extends RecyclerView.ViewHolder {

        final MaterialTextView Cname;
        final MaterialTextView subject;
        final MaterialTextView Caddress;
        final MaterialTextView Cmobile;
        final MaterialTextView Cemail;

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
        bundle.putString("Cname", item.getCname());
        bundle.putString("Caddress", item.getCaddress());
        bundle.putString("Cmobile", item.getCmobile());
        bundle.putString("Cemail", item.getCemail());
        bundle.putString("subject", item.getSubject());
        Intent intent = new Intent(context, add_company.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void deleteData(int position) {
        recycle_getter_setter item = companyArrayList.get(position);
        fstore.collection("Companies").document(item.getid()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Company Deleted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error:" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
