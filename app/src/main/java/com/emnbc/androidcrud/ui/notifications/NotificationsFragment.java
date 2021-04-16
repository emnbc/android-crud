package com.emnbc.androidcrud.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emnbc.androidcrud.MainActivity;
import com.emnbc.androidcrud.R;
import com.emnbc.androidcrud.services.RunTimer;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RunTimer runTimer;
    private Button btn;
    private String startSnd;
    private String stopSnd;
    private int colorGreen;
    private int colorRed;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);

        startSnd = getResources().getString(R.string.sending_state_start);
        stopSnd = getResources().getString(R.string.sending_state_stop);

        colorGreen = ContextCompat.getColor(getContext(), R.color.color_green);
        colorRed = ContextCompat.getColor(getContext(), R.color.color_red);

        runTimer = ((MainActivity) getActivity()).getRunTimer();
        btn = (Button) root.findViewById(R.id.buttonToggle);
        initBtn(btn, runTimer.getStatus());

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

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (runTimer.getStatus()) {
                            runTimer.stopSending();
                            btn.setText(startSnd);
                            btn.setBackgroundColor(colorGreen);
                        } else {
                            runTimer.startSending();
                            btn.setText(stopSnd);
                            btn.setBackgroundColor(colorRed);
                        }

                    }
                }
        );

    }

    private void initBtn(Button btn, Boolean state) {
        if (state) {
            btn.setText(stopSnd);
            btn.setBackgroundColor(colorRed);
        } else {
            btn.setText(startSnd);
            btn.setBackgroundColor(colorGreen);
        }
    }
}