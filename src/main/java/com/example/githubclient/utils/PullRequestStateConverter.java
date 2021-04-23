package com.example.githubclient.utils;

import com.example.githubclient.model.PullRequestState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class PullRequestStateConverter implements Converter<String, PullRequestState> {
    @Override
    public PullRequestState convert(String value) {
        return PullRequestState.valueOf(value.toUpperCase(Locale.ROOT));
    }
}

