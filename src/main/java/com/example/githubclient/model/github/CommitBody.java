package com.example.githubclient.model.github;

public class CommitBody {
    public CommitBody() {
    }

    public CommitBody(final String message, final String sha) {
        this.message = message;
        this.sha = sha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(final String sha) {
        this.sha = sha;
    }

    private String message;
    private String sha;
}
