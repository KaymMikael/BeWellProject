package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList id, username, password;

    public CustomerAdapter(Context context, ArrayList id, ArrayList username, ArrayList password) {
        this.context = context;
        this.id = id;
        this.username = username;
        this.password = password;

    }

    @NonNull
    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.data_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.txtId.setText(String.valueOf(id.get(position)));
        myViewHolder.txtUsername.setText(String.valueOf(username.get(position)));
        myViewHolder.txtPass.setText(String.valueOf(password.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtUsername, txtPass;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtPass = itemView.findViewById(R.id.txtPass);
        }
    }
}