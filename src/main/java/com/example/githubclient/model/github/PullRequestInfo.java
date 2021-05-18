package com.example.githubclient.model.github;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PullRequestInfo {
    private String title;
    private Long number;

    public PullRequestInfo(final String title) {
        this.title = title;
    }

    public PullRequestInfo() {
    }

    @SerializedName("updated_at")
    private Date updatedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


}
