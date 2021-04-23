package com.example.githubclient.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentBody {
    private String body;
    private Integer position = 1;
    private String path;
    @SerializedName("commit_id")
    private String commitId;

    public CommentBody(String body) {
        this.body = body;
    }
}
