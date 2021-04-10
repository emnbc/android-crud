package com.emnbc.androidcrud.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emnbc.androidcrud.R;
import com.emnbc.androidcrud.models.Item;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DashboardFragment extends Fragment implements ItemViewAdapter.ItemClickListener {

    private View root;
    private Button btn;
    private ArrayList<Item> itemNames;
    private RecyclerView recyclerView;
    private ItemViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        itemNames = new ArrayList<>();
        recyclerView = root.findViewById(R.id.rvItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemViewAdapter(getContext(), itemNames);
        recyclerView.setAdapter(adapter);

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

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
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
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson = new Gson();
            Item[] itemsArray = gson.fromJson(s, Item[].class);

            itemNames.clear();

            for(int i = 0; i < itemsArray.length; i++) {
                itemNames.add(itemsArray[i]);
            }

            adapter = new ItemViewAdapter(getContext(), itemNames);
            adapter.setClickListener(DashboardFragment.this);
            recyclerView.setAdapter(adapter);
        }
    }
}