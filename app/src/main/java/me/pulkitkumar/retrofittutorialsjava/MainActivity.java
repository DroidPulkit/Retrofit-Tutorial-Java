package me.pulkitkumar.retrofittutorialsjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doSomething();
    }

    void doSomething(){
        String API_BASE_URL = "https://api.github.com/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        GitHubClient client = retrofit.create(GitHubClient.class);

        Call<List<GitHubRepo>> call = client.repoForUsers("droidpulkit");

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                for (GitHubRepo repo : response.body()){
                    Log.d(TAG, "Repo ID: " + repo.getId() + " Repo Name: " + repo.getName());
                }
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}
