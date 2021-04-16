package com.emnbc.androidcrud.services;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.Toast;

public class RunTimer {

    private LayoutInflater mInflater;
    private Activity mActivity;
    private Boolean isActive = false;

    public RunTimer(Activity activity) {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
    }

    public void startSending() {
        if (!isActive) {
            isActive = true;
            sending();
        }
    }

    public void stopSending() {
        isActive = false;
    }

    public Boolean getStatus() {
        return isActive;
    }

    private void sending() {
        mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isActive) {
                        Toast.makeText(mInflater.getContext(), "Test", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    sending();
                                }
                            }, 3000);
                    }
                }
            }
        );
    }
}
