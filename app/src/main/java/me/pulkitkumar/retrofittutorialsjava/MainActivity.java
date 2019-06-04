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

        GitHubClient client = ServiceGenerator.createService(GitHubClient.class);

        // Fetch a list of the Github repositories.
        Call<List<GitHubRepo>> call = client.repoForUsers("droidpulkit");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<GitHubRepo>>() {

            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                //The network call is successful

                List<GitHubRepo> gitHubRepoList = response.body();
                if (gitHubRepoList != null && gitHubRepoList.size() > 0) {
                    githubListingAdapter = new GithubListingAdapter(getApplicationContext(), gitHubRepoList);
                    gitHubListing.setAdapter(githubListingAdapter);
                    githubListingAdapter.notifyDataSetChanged();

                    for (GitHubRepo repo : gitHubRepoList) {
                        Log.d(TAG, "Repo ID: " + repo.getId() + " Repo Name: " + repo.getName());
                    }
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
