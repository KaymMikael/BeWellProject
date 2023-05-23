package com.example.testapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapp.DB;
import com.example.testapp.MainActivity;
import com.example.testapp.R;
import com.example.testapp.ui.home.HomeFragment;

public class RegisterActivity extends AppCompatActivity {
    private EditText registerName, registerPassword, registerRePassword;
    private Button btnRegister;

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DB(this);

        registerName = findViewById(R.id.registerName);
        registerPassword = findViewById(R.id.registerPassword);
        registerRePassword = findViewById(R.id.registerRePassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = registerName.getText().toString();
                String pass = registerPassword.getText().toString();
                String repass = registerRePassword.getText().toString();
                if(user.isEmpty()||pass.isEmpty()||repass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if(pass.equals(repass)) {
                        Boolean checkUser = db.checkUsername(user);
                        if(checkUser == false) {
                            Boolean insert = db.insertData(user, pass);
                            if(insert == true) {
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this,"Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "User already Exists, please sign in!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}