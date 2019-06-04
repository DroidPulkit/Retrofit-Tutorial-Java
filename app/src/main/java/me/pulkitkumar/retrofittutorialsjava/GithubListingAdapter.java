package me.pulkitkumar.retrofittutorialsjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * This is standard RecyclerView Adapter which is used to assign list of data in RV
 */
public class GithubListingAdapter extends RecyclerView.Adapter<GithubListingAdapter.GithubListingAdapterViewHolder>

{

    private Context mContext;
    private List<GitHubRepo> mGitHubRepoList;

    GithubListingAdapter(Context context){
        mContext = context;
        mGitHubRepoList = new ArrayList<>();
    }

    GithubListingAdapter(Context context, List<GitHubRepo> gitHubRepoList){
        mContext = context;
        mGitHubRepoList = gitHubRepoList;
    }

    @NonNull
    @Override
    public GithubListingAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GithubListingAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.github_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GithubListingAdapterViewHolder holder, int position) {
        holder.id.setText(Integer.toString(mGitHubRepoList.get(position).getId()));
        holder.name.setText(mGitHubRepoList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mGitHubRepoList.size();
    }

    class GithubListingAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView id, name;

        public GithubListingAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.github_project_id);
            name = itemView.findViewById(R.id.github_project_name);
        }
    }
}
