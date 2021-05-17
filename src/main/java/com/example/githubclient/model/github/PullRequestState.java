package com.example.githubclient.model.github;

public enum PullRequestState {
    OPEN("open"),
    CLOSED("closes"),
    ALL("all");

    PullRequestState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private final String value;
}
