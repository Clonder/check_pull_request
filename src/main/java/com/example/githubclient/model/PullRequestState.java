package com.example.githubclient.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PullRequestState {
    OPEN("open"), CLOSED("closes"), ALL("all");
    private final String value;
}
