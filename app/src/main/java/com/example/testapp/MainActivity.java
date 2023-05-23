package com.example.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapp.databinding.ActivityMainBinding;
import com.example.testapp.ui.RegisterActivity;
import com.example.testapp.ui.secondpage.GalleryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DB db;


    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button btnLogIn, btnRegister, btnReport2;
    private EditText txtUsername, txtPassword, txtUserName2, txtReport2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "\"Unite against cyberbullying with BeWell, where kindness creates change.\"", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        binding.appBarMain.fab.setVisibility(View.GONE);
        db = new DB(this);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_test, R.id.nav_admin, R.id.nav_report)
                .setOpenableLayout(drawer)
                .build();

        btnLogIn = findViewById(R.id.btnLogIn);
        btnRegister = findViewById(R.id.btnRegister);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        FloatingActionButton fab = binding.appBarMain.fab;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.nav_slideshow) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });


        MenuItem nav_gallery = navigationView.getMenu().findItem(R.id.nav_gallery);
        MenuItem nav_slideshow = navigationView.getMenu().findItem(R.id.nav_slideshow);
        MenuItem nav_test = navigationView.getMenu().findItem(R.id.nav_test);
        MenuItem nav_home = navigationView.getMenu().findItem(R.id.nav_home);
        MenuItem nav_admin = navigationView.getMenu().findItem(R.id.nav_admin);
        MenuItem nav_report = navigationView.getMenu().findItem(R.id.nav_report);
        nav_gallery.setVisible(false);
        nav_test.setVisible(false);
        nav_slideshow.setVisible(false);
        nav_admin.setVisible(false);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtUsername.getText().toString();
                String pass = txtPassword.getText().toString();
                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserPass = db.checkUsernamePassword(user, pass);
                    if (checkUserPass == true) {
                        if (user.equals("admin")) {
                            nav_admin.setVisible(true);
                        }
                        Toast.makeText(MainActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                        nav_home.setVisible(false);
                        nav_gallery.setVisible(true);
                        nav_test.setVisible(true);
                        nav_slideshow.setVisible(true);
                        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                        navController.navigate(R.id.nav_gallery);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavDestination currentDestination = navController.getCurrentDestination();
        if (currentDestination != null && currentDestination.getId() != R.id.nav_home) {
            navController.navigate(R.id.nav_home);
        } else {
            super.onBackPressed();
        }
    }
}