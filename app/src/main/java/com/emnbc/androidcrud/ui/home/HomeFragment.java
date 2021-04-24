package com.emnbc.androidcrud.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.emnbc.androidcrud.MainActivity;
import com.emnbc.androidcrud.R;
import com.emnbc.androidcrud.services.ForegroundService;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btn, btnStart, btnStop, btnCheck;
    private TextView textView;
    private MainActivity mActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        mActivity = (MainActivity) getActivity();
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        if (mActivity.isMyServiceRunning(ForegroundService.class)) {
            textView.setText("Running");
        } else {
            textView.setText("Stopped");
        }

        addListenerOnButtons(root);

        return root;
    }

    public void addListenerOnButtons(View v) {
        btn = (Button) v.findViewById(R.id.button);
        btnStart = (Button) v.findViewById(R.id.buttonStart);
        btnStop = (Button) v.findViewById(R.id.buttonStop);
        btnCheck = (Button) v.findViewById(R.id.buttonCheck);

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
                        if (!mActivity.isMyServiceRunning(ForegroundService.class)) {
                            mActivity.startForegroundService();
                            textView.setText("Running");
                            if (!mActivity.getBound()) {
                                mActivity.bindService();
                            }
                        }
                    }
                }
        );

        btnStop.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mActivity.isMyServiceRunning(ForegroundService.class)) {
                            if (mActivity.getBound()) {
                                mActivity.unbindService();
                            }
                            mActivity.stopForegroundService();
                            textView.setText("Stopped");
                        }
                    }
                }
        );

        btnCheck.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mActivity.isMyServiceRunning(ForegroundService.class) && mActivity.getBound()) {
                            String str = mActivity.getMService().getRandomNumber() + "";
                            textView.setText(str);
                        }
                    }
                }
        );
    }

}