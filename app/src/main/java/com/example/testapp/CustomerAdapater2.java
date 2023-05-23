package com.example.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapater2 extends RecyclerView.Adapter<CustomerAdapater2.MyViewHolder> {
    private Context context;
    private ArrayList UserName, report;

    public CustomerAdapater2(Context context, ArrayList UserName, ArrayList report) {
        this.context = context;
        this.UserName = UserName;
        this.report = report;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.data_row2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.txtUserName.setText(String.valueOf(UserName.get(position)));
        myViewHolder.txtReport.setText(String.valueOf(report.get(position)));
    }

    @Override
    public int getItemCount() {
        return UserName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName, txtReport;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtReport = itemView.findViewById(R.id.txtReport);
        }
    }
}
