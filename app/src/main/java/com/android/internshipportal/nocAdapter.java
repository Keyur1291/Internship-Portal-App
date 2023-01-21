package com.android.internshipportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class nocAdapter extends RecyclerView.Adapter<nocAdapter.myViewHolder> {

    final Context context;
    final ArrayList<recycle_getter_setter> nocArrayList;

    public nocAdapter(Context context, ArrayList<recycle_getter_setter> nocArrayList) {
        this.context = context;
        this.nocArrayList = nocArrayList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.noc_list_item, parent,false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        recycle_getter_setter recycle_getter_setter = nocArrayList.get(position);

        holder.name.setText(recycle_getter_setter.name);
        holder.enrollment.setText(recycle_getter_setter.enrollment);
        holder.department.setText(recycle_getter_setter.department);
        holder.Cmobile.setText(recycle_getter_setter.Cmobile);
        holder.Cemail.setText(recycle_getter_setter.Cemail);
        holder.Cname.setText(recycle_getter_setter.Cname);
        holder.Caddress.setText(recycle_getter_setter.Caddress);
        holder.subject.setText(recycle_getter_setter.subject);

    }

    @Override
    public int getItemCount() {
        return nocArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        final MaterialTextView name;
        final MaterialTextView enrollment;
        final MaterialTextView department;
        final MaterialTextView Cmobile;
        final MaterialTextView Cemail;
        final MaterialTextView Cname;
        final MaterialTextView Caddress;
        final MaterialTextView subject;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lName);
            enrollment = itemView.findViewById(R.id.lEnno);
            department = itemView.findViewById(R.id.lDdept);
            Cmobile = itemView.findViewById(R.id.cMobile);
            Cemail = itemView.findViewById(R.id.cEmail);
            Cname = itemView.findViewById(R.id.cName);
            Caddress = itemView.findViewById(R.id.cAddress);
            subject = itemView.findViewById(R.id.subject);
        }
    }
}
