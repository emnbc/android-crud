package com.emnbc.androidcrud.ui.dashboard;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mItems;

    public DashboardViewModel() {
        mItems = new MutableLiveData<>();
    }

    public LiveData<String> getItems() {
        return mItems;
    }


    public void getHttpResponse() throws IOException {

        String url = "http://pizzashop.emnbc.com/api/pizzas";

        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                mItems.postValue(mMessage);
            }
        });
    }

    public void postHttpResponse() throws IOException {

        String url = "http://track-me.emnbc.com/api/track/send";

        OkHttpClient httpClient = new OkHttpClient();
        final MediaType mediaType = MediaType.parse("application/json");

        String jsonStr = "{\"trackId\":\"868239606757\", \"lngX\" : 54.795899, \"latY\" : 55.959599}";

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, jsonStr))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                Log.e("TEST POST", mMessage);
            }
        });
    }
}