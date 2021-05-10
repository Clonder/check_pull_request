package com.example.githubclient;

public class MessageTemplateVerifier {

    private static final String TITLE_TEMPLATE =
        "^(GENERATOR|LEETCODE)\\s(1021|1022|1013|2021|2022)\\s(Added|Deleted|Refactored|Moved|Fixed)";

    public static boolean process(final String pullRequestInfo) {
        return pullRequestInfo.matches(TITLE_TEMPLATE);
    }
}

