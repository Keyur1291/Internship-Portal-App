package com.android.internshipportal;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.myViewHolder> {

    Context context;
    ArrayList<recycle_getter_setter> userArrayList;

    public userAdapter(Context context, ArrayList<recycle_getter_setter> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public userAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.students_list_item, parent,false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.myViewHolder holder,int position) {

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

    public static class myViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView name, enrollment, department, mobile, email;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lName);
            enrollment = itemView.findViewById(R.id.lEnno);
            department = itemView.findViewById(R.id.lDdept);
            mobile = itemView.findViewById(R.id.lMobile);
            email = itemView.findViewById(R.id.lEmail);
        }
    }

}
