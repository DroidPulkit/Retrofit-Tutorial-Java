package me.pulkitkumar.retrofittutorialsjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This activity uses the Retrofit to call the Github API and then,
 * passes the data to recyclerView so that the data is shown on the android screen
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    RecyclerView gitHubListing;
    GithubListingAdapter githubListingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init of recyclerView and adding default adapter
        gitHubListing = findViewById(R.id.rv_github_listing);
        gitHubListing.setLayoutManager(new LinearLayoutManager(this));
        githubListingAdapter = new GithubListingAdapter(this);
        gitHubListing.setAdapter(githubListingAdapter);

        useRetrofit();
    }

    /**
     * This function has all the logic to init the retrofit
     */
    void useRetrofit(){

        //This is to setup the base url on which we are calling the api
        String API_BASE_URL = "https://api.github.com/";

        //Making an OkHttpClient Builder which opens up the socket to the URL above
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        //Making the builder for the url and adding a converter
        // through which we can map the JSON response we get from API to Java object
        // We are using GSON, right now for that.
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        //Finally adding OkHttpClient and Retrofit together
        Retrofit retrofit = builder.client(httpClient.build()).build();

        //This is used to initialize the interface,
        // which is later used to call the functions,
        // we created to call the endpoints
        GitHubClient client = retrofit.create(GitHubClient.class);

        // Fetch a list of the Github repositories.
        Call<List<GitHubRepo>> call = client.repoForUsers("droidpulkit");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<GitHubRepo>>() {

            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                //The network call is successful

                List<GitHubRepo> gitHubRepoList = response.body();

                githubListingAdapter = new GithubListingAdapter(getApplicationContext(), gitHubRepoList);
                gitHubListing.setAdapter(githubListingAdapter);
                githubListingAdapter.notifyDataSetChanged();

                for (GitHubRepo repo : response.body()){
                    Log.d(TAG, "Repo ID: " + repo.getId() + " Repo Name: " + repo.getName());
                }


            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {

                //The network call failed

                t.printStackTrace();
            }
        });


    }
}
