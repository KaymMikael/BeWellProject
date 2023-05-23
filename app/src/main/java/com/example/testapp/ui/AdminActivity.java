package com.example.testapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapp.AdminViewModel;
import com.example.testapp.CustomerAdapter;
import com.example.testapp.DB;
import com.example.testapp.R;
import com.example.testapp.databinding.FragmentAdminBinding;
import com.example.testapp.databinding.FragmentTestBinding;
import com.example.testapp.ui.fourthpage.TestViewModel;

import java.util.ArrayList;

public class AdminActivity extends Fragment {
    private FragmentAdminBinding binding;
    DB db;
    private EditText remove;
    private Button btnRemove;
    ArrayList<String> id, username, password;
    RecyclerView allData;
    CustomerAdapter customerAdapter;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminViewModel testViewModel =
                new ViewModelProvider(this).get(AdminViewModel.class);

        binding = FragmentAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        id = new ArrayList<>();
        username = new ArrayList<>();
        password = new ArrayList<>();

        allData = root.findViewById(R.id.allData);
        remove = root.findViewById(R.id.remove);
        btnRemove = root.findViewById(R.id.btnRemove);
        db = new DB(getContext());
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delete = remove.getText().toString();
                if (delete.isEmpty()) {
                    Toast.makeText(getContext(), "Enter user you want to delete", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isDeleted = db.removeData(delete, getContext());
                    if (isDeleted) {
                        Toast.makeText(getContext(), "User Deleted", Toast.LENGTH_SHORT).show();
                        id.clear();
                        username.clear();
                        password.clear();
                        storeDataInArrays();
                        customerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        storeDataInArrays();
        customerAdapter = new CustomerAdapter(getContext(), id, username, password);
        allData.setAdapter(customerAdapter);
        allData.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    void storeDataInArrays() {
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                username.add(cursor.getString(1));
                password.add(cursor.getString(2));
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Sign Out")
                    .setMessage("Do you want to sign out?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
