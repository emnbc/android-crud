package com.emnbc.androidcrud.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emnbc.androidcrud.MainActivity;
import com.emnbc.androidcrud.R;
import com.emnbc.androidcrud.services.RunTimer;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RunTimer runTimer;
    private Button btnStart;
    private Button btnStop;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);

        runTimer = ((MainActivity) getActivity()).getRunTimer();

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        addListenerOnButtons(root);
        return root;
    }

    public void addListenerOnButtons(View v) {
        btnStart = (Button) v.findViewById(R.id.buttonStart);
        btnStop = (Button) v.findViewById(R.id.buttonStop);

        btnStart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runTimer.startSending();
                        // Toast.makeText(getActivity(),"Hello world!",Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnStop.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runTimer.stopSending();
                        // Toast.makeText(getActivity(),"Hello world!",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}