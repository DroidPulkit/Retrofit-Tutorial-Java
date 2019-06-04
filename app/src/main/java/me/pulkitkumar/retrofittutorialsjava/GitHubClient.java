package me.pulkitkumar.retrofittutorialsjava;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {

    //This is the function which defines, what endpoint to call and what JSON to java data to map to
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> repoForUsers (
            @Path("user") String user
    );
}
