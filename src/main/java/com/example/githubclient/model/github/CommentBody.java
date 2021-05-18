package com.example.githubclient.model.github;

import com.google.gson.annotations.SerializedName;

public class CommentBody {
    private String body;
    private Integer position = 1;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    private String path;

    public CommentBody(String body, Integer position, String path, String commitId) {
        this.body = body;
        this.position = position;
        this.path = path;
        this.commitId = commitId;
    }

    @SerializedName("commit_id")
    private String commitId;

    public CommentBody(String body) {
        this.body = body;
    }
}
