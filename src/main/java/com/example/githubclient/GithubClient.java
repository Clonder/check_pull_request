package com.example.githubclient;

import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static com.example.githubclient.MessageTemplateVerifier.process;

@Service
public class GithubClient {
    public static final String VERIFICATION_RESULT = "VERIFICATION RESULT";

    static final String API_BASE_URL = "https://api.github.com/";
    static final String API_VERSION_SPEC = "application/vnd.github.v3+json";
    static final String JSON_CONTENT_TYPE = "application/json";

    private String accessToken;


    private GithubApiInterface service;

    public GithubClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubApiInterface.class);
        this.accessToken = "token ";
    }

    public void validatePullRequestTitles(String owner, String repoName) throws IOException {
        var pullRequestInfo = service.getAllPulls(accessToken, API_VERSION_SPEC, repoName, owner);

        var response = pullRequestInfo.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        response.body().forEach(pullRequest -> {
            if (!process(pullRequest.getTitle())) {
                try {
                    addComment(owner, repoName, pullRequest.getNumber(), "Wrong title");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void addComment(String owner, String repoName, Long number, String comment) throws IOException {
        var retrofitCall = service.addComment(accessToken, API_VERSION_SPEC, repoName, owner,
                number, VERIFICATION_RESULT + " " + comment);

        var response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

    }
}