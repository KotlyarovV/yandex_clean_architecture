package com.github.arch.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.arch.R;
import com.github.arch.domain.Repo;

import java.util.List;

public class RepoRecyclerAdapter extends RecyclerView.Adapter<RepoRecyclerAdapter.RepoViewHolder> {

    @Nullable
    private List<Repo> mRepos;

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Repo repo = getItem(position);
        if (repo == null) {
            throw new IllegalStateException("Repo might not be null");
        }
        holder.mRepoName.setText(repo.getName());

        String idFullName = holder.itemView.getContext().getString(R.string.id_full_name,
                repo.getId(), repo.getFullName());
        holder.mRepoIdFullName.setText(idFullName);
        holder.itemView.setOnClickListener(view -> {
            RepoInformationFragment repoInformationFragment = RepoInformationFragment.getInstance(repo.getId());

            ((MainActivity)view.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, repoInformationFragment, RepoInformationFragment.TAG)
                    .commit();
        });
    }

    public void setRepos(@Nullable List<Repo> repos) {
        if (repos == null) {
            notifyItemRangeRemoved(0, getItemCount());
            mRepos = null;
        } else if (mRepos == null) {
            mRepos = repos;
            notifyItemRangeInserted(0, repos.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mRepos.size();
                }

                @Override
                public int getNewListSize() {
                    return repos.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mRepos.get(oldItemPosition).getId() ==
                            repos.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Repo newRepo = repos.get(newItemPosition);
                    Repo oldRepo = mRepos.get(oldItemPosition);
                    return newRepo.getId() == oldRepo.getId()
                            && TextUtils.equals(newRepo.getName(), oldRepo.getName())
                            && TextUtils.equals(newRepo.getFullName(), oldRepo.getFullName());
                }
            });
            mRepos = repos;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemCount() {
        return mRepos == null ? 0 : mRepos.size();
    }

    public Repo getItem(int pos) {
        return mRepos == null ? null : mRepos.get(pos);
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        @SuppressWarnings("NullableProblems")
        @NonNull
        private TextView mRepoName;

        @SuppressWarnings("NullableProblems")
        @NonNull
        private TextView mRepoIdFullName;

        RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            mRepoName = itemView.findViewById(R.id.repo_name);
            mRepoIdFullName = itemView.findViewById(R.id.repo_id_full_name);
        }
    }
}
