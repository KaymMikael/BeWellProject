package com.example.testapp.ui.secondpage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.testapp.MainActivity;
import com.example.testapp.R;
import com.example.testapp.ReportActivity;
import com.example.testapp.databinding.FragmentGalleryBinding;
import com.example.testapp.ui.RegisterActivity;

import java.time.LocalTime;

public class GalleryFragment extends Fragment {
    @SuppressLint("NewApi")
    LocalTime time = LocalTime.now();
    @SuppressLint("NewApi")
    int hour = time.getHour();

    private FragmentGalleryBinding binding;
    Button btnReport;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        TextView txtMessage = (TextView) root.findViewById(R.id.txtMessage);
        if (hour > 0 && hour < 12) {
            txtMessage.setText("Hello, Good Morning");
        } else if (hour >= 12 && hour < 18) {
            txtMessage.setText("Hello, Good Afternoon");
        } else {
            txtMessage.setText("Hello, Good Evening");
        }
        Button btnDiscord = (Button) root.findViewById(R.id.btnDiscord);
        Button btnMessenger = (Button) root.findViewById(R.id.btnMessenger);
        Button btnFacebook = (Button) root.findViewById(R.id.btnFacebook);
        btnReport = root.findViewById(R.id.btnReport);
        btnDiscord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String discordLink = "https://discord.gg/7wEZgpZkQZ";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(discordLink));
                startActivity(intent);
            }
        });
        btnMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messengerLink = "https://m.me/j/AbZeCWFnoh1UyZ0P/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(messengerLink));
                startActivity(intent);
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String facebookLink = "https://www.facebook.com/BeWell-108392347288593";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookLink));
                startActivity(intent);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                startActivity(intent);
            }
        });
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
}