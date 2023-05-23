package com.example.testapp.ui.fourthpage;

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
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.CustomerAdapater2;
import com.example.testapp.CustomerAdapter;
import com.example.testapp.DB;
import com.example.testapp.R;
import com.example.testapp.databinding.FragmentTestBinding;

import java.util.ArrayList;


public class TestFragment extends Fragment {
    private FragmentTestBinding binding;
    DB db;
    ArrayList<String> UserName, report;
    RecyclerView reportedData;
    CustomerAdapater2 customerAdapter;

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TestViewModel testViewModel =
                new ViewModelProvider(this).get(TestViewModel.class);

        binding = FragmentTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        UserName = new ArrayList<>();
        report = new ArrayList<>();
        reportedData = root.findViewById(R.id.reportedData);
        db = new DB(getContext());
        reportedDataInArrays();
        customerAdapter = new CustomerAdapater2(getContext(), UserName, report);
        reportedData.setAdapter(customerAdapter);
        reportedData.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
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
    void reportedDataInArrays() {
        Cursor cursor = db.readAllData2();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                UserName.add(cursor.getString(0));
                report.add(cursor.getString(1));
            }
        }
    }
}
