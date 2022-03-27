package com.android.internshipportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class companyAdapter extends RecyclerView.Adapter<companyAdapter.myViewHolder> {

    Context context;
    ArrayList<recycle_getter_setter> companyArrayList;

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
}
