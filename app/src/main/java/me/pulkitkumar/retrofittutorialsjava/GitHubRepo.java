package me.pulkitkumar.retrofittutorialsjava;

import retrofit2.Call;

/**
 * This is the model class which is used to map JSON to Java objects
 */
public class GitHubRepo {

    private int id;
    private String name;

    public GitHubRepo() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
