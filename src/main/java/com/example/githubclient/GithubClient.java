package com.example.githubclient;

import com.example.githubclient.model.*;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import static com.example.githubclient.MessageTemplateVerifier.process;

@Service
public class GithubClient {
    public static final String VERIFICATION_RESULT = "VERIFICATION RESULT";

    static final String API_BASE_URL = "https://api.github.com/";
    static final String API_VERSION_SPEC = "application/vnd.github.v3+json";

    private final String accessToken;


    private final GithubApiInterface service;


    public GithubClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubApiInterface.class);
        this.accessToken = "token ";
    }

    public <T> T executeWithResult(Supplier<Call<T>> retrofitCall) throws IOException {
        var pullRequestInfo = retrofitCall.get();

        var response = pullRequestInfo.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }


    public List<PullRequestInfo> getAllPulls(String owner, String repository, PullRequestState state)
            throws IOException {
        return executeWithResult(() -> service.getAllPulls(accessToken, API_VERSION_SPEC, repository,
                owner, state.getValue()));
    }

    public List<CommitInfo> getAllCommits(String owner, String repository, Long pullNumber) throws IOException {

        return executeWithResult(() -> service.getAllCommitsForPull(accessToken, API_VERSION_SPEC, repository,
                owner, pullNumber));

    }

    public List<CommentResponse> getAllIssueComments(String owner, String repository, Long issueNumber)
            throws IOException {

        return executeWithResult(() -> service.getAllIssueCommentsForPull(accessToken,
                API_VERSION_SPEC, repository,
                owner, issueNumber));
    }

    public List<CommentResponse> getAllReviewComments(String owner, String repository, Long pullNumber)
            throws IOException {

        return executeWithResult(() -> service.getAllReviewCommentsForPull(accessToken,
                API_VERSION_SPEC, repository,
                owner, pullNumber));
    }

    public void addReviewComment(String owner,
                                 String repoName,
                                 Long pullNumber,
                                 String commitId,
                                 String comment,
                                 Integer position,
                                 String filePath) throws IOException {
        var retrofitCall = service.addReviewComment(accessToken, API_VERSION_SPEC, repoName, owner,
                pullNumber, new CommentBody(VERIFICATION_RESULT + " " + comment, position, filePath, commitId));

        var response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

    }

    public void addIssueComment(String owner,
                                String repoName,
                                Long issueNumber,
                                String comment) throws IOException {
        var retrofitCall = service.addIssueComment(accessToken, API_VERSION_SPEC, repoName, owner,
                issueNumber, new CommentBody(VERIFICATION_RESULT + " " + comment));

        var response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

    }

    public void validatePullRequestTitles(String owner, String repository, PullRequestState state) throws IOException {
        getAllPulls(owner, repository, state).forEach(pullRequest -> {
            if (!process(pullRequest.getTitle())) {
                try {
                    addIssueComment(owner, repository, pullRequest.getNumber(), "Wrong title");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void validatePullRequestCommitTitles(String owner, String repository, Long pullRequestId) throws IOException {
        getAllCommits(owner, repository, pullRequestId).forEach(commitInfo -> {
            if (!process(commitInfo.getCommit().getMessage())) { //ToDo
                try {
                    addIssueComment(owner, repository, pullRequestId, "Wrong title for this commit " +
                            commitInfo.getCommit().getSha());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}