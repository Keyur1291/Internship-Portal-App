package com.android.internshipportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

    Context context;
    ArrayList<user_list> userArrayList;

    public myAdapter(Context context, ArrayList<user_list> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public myAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter.myViewHolder holder, int position) {

        user_list user_list = userArrayList.get(position);

        holder.name.setText(user_list.name);
        holder.enrollment.setText(user_list.enrollment);
        holder.department.setText(user_list.department);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView name, enrollment, department;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lName);
            enrollment = itemView.findViewById(R.id.lEnno);
            department = itemView.findViewById(R.id.lDept);
        }
    }
}
