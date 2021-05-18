package com.example.githubclient.model.github;

public class CommitInfo {
    public CommitInfo() {
    }

    public CommitInfo(final CommitBody commit) {
        this.commit = commit;
    }

    public CommitBody getCommit() {
        return commit;
    }

    public void setCommit(final CommitBody commit) {
        this.commit = commit;
    }

    private CommitBody commit;
}
