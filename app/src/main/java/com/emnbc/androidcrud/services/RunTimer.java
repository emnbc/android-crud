package com.emnbc.androidcrud.services;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.Toast;
import java.util.Timer;

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

    public void  stopSending() {
        isActive = false;
    }

    private void sending() {
        mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                if (isActive) {
                                    Toast.makeText(mInflater.getContext(), "Test", Toast.LENGTH_SHORT).show();
                                    sending();
                                }
                            }
                        }, 3000);
                }
            }
        );
//        new Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        Log.i("tag","A Kiss after 5 seconds");
//                        Run();
//                    }
//                }, 1000);
    }
}
