package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapp.ui.fourthpage.TestFragment;
import com.example.testapp.ui.home.HomeFragment;
import com.example.testapp.ui.home.HomeViewModel;
import com.example.testapp.ui.secondpage.GalleryFragment;

public class ReportActivity extends AppCompatActivity {
    DB db;
    EditText txtUserName2, txtReport2;
    Button btnReport2;
    private static final int delaySeconds = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        db = new DB(this);
        txtUserName2 = findViewById(R.id.txtUserName2);
        txtReport2 = findViewById(R.id.txtReport2);
        btnReport2 = findViewById(R.id.btnReport2);
        btnReport2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtUserName2.getText().toString();
                String report = txtReport2.getText().toString();
                if(name.isEmpty() || report.isEmpty()) {
                    Toast.makeText(ReportActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReportActivity.this, "Reported Successfully.", Toast.LENGTH_SHORT).show();
                    db.insertReport(name, report);
                    txtUserName2.setText("");
                    txtReport2.setText("");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, delaySeconds);
                }
            }
        });
    }
}