package com.emnbc.androidcrud.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emnbc.androidcrud.MainActivity;
import com.emnbc.androidcrud.R;
import com.emnbc.androidcrud.services.ForegroundService;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btn, btnStart, btnStop;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        addListenerOnButtons(root);

        return root;
    }

    public void addListenerOnButtons(View v) {
        btn = (Button) v.findViewById(R.id.button);
        btnStart = (Button) v.findViewById(R.id.buttonStart);
        btnStop = (Button) v.findViewById(R.id.buttonStop);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"Hello world!",Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnStart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).startForegroundService();
                    }
                }
        );

        btnStop.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).stopForegroundService();
                    }
                }
        );
    }

//    public void startForegroundService() {
//        Intent serviceIntent = new Intent(getContext(), ForegroundService.class);
//        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
//        ContextCompat.startForegroundService(getContext(), serviceIntent);
//    }
//
//    public void stopForegroundServiceN() {
//        Intent serviceIntent = new Intent(getContext(), ForegroundService.class);
//        getActivity().stopService(serviceIntent);
//    }

}