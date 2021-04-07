package com.emnbc.androidcrud.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emnbc.androidcrud.R;
import com.emnbc.androidcrud.models.Item;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button btn;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        addListenerOnButton(root);
        return root;
    }

    public void addListenerOnButton(View v) {
        btn = (Button) v.findViewById(R.id.button2);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AsyncRequest().execute();
                    }
                }
        );
    }

    class AsyncRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {

            OkHttpClient httpClient = new OkHttpClient();

            String url = "http://pizzashop.emnbc.com/api/pizzas";
            Request request = new Request.Builder().url(url).build();

            Response response = null;
            try {
                response = httpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                textView.setText("Fuck");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson = new Gson();
            Item[] userArray = gson.fromJson(s, Item[].class);
            textView.setText(
                    "1 - " + userArray[0].getName() + "\n" +
                    "2 - " + userArray[1].getName()
            );
        }
    }
}