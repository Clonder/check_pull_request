package com.example.githubclient.service;

import com.example.githubclient.GithubApiInterface;
import com.example.githubclient.model.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

import static com.example.githubclient.MessageTemplateVerifier.process;

@Service
public class GithubClient {
    public static final String VERIFICATION_RESULT = "VERIFICATION RESULT";

    private static final String API_VERSION_SPEC = "application/vnd.github.v3+json";


    @Value("token ${GITHUB_TOKEN}")
    private String accessToken;


    private final GithubApiInterface service;

    public GithubClient(GithubApiInterface service) {
        this.service = service;
    }

    /**
     * Generic function that sends a request to a webserver and returns a response.
     */
    private <T> T executeWithResult(Call<T> retrofitCall)
            throws IOException {

        var response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }
        return response.body();
    }


    public boolean isUserExist(final String username) {
        try {
            executeWithResult(service.getUser(accessToken, API_VERSION_SPEC, username));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserRepositoryExist(final String owner, final String repository) {
        try {
            executeWithResult(service.getRepository(accessToken, API_VERSION_SPEC, repository, owner));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PullRequestInfo> getAllPulls(String owner,
                                             String repository,
                                             PullRequestState state)
            throws IOException {
        return executeWithResult(service.getAllPulls(accessToken, API_VERSION_SPEC, repository,
                owner, state.getValue()));
    }

    public List<CommitInfo> getAllCommits(String owner,
                                          String repository,
                                          Long pullNumber)
            throws IOException {

        return executeWithResult(service.getAllCommitsForPull(accessToken, API_VERSION_SPEC, repository,
                owner, pullNumber));

    }

    public List<CommentResponse> getAllIssueComments(String owner,
                                                     String repository,
                                                     Long issueNumber)
            throws IOException {

        return executeWithResult(service.getAllIssueCommentsForPull(accessToken,
                API_VERSION_SPEC, repository,
                owner, issueNumber));
    }

    public List<CommentResponse> getAllReviewComments(String owner,
                                                      String repository,
                                                      Long pullNumber)
            throws IOException {

        return executeWithResult(service.getAllReviewCommentsForPull(accessToken,
                API_VERSION_SPEC, repository,
                owner, pullNumber));
    }

    public void addReviewComment(String owner,
                                 String repoName,
                                 Long pullNumber,
                                 String commitId,
                                 String comment,
                                 Integer position,
                                 String filePath)
            throws IOException {

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
                                String comment)
            throws IOException {

        var retrofitCall = service.addIssueComment(accessToken, API_VERSION_SPEC, repoName, owner,
                issueNumber, new CommentBody(VERIFICATION_RESULT + " " + comment));

        var response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

    }


    public void validatePullRequest(String owner,
                                    String repository,
                                    PullRequestState state)
            throws IOException {

        getAllPulls(owner, repository, state).forEach(pullRequest -> {
            final StringBuilder comment = new StringBuilder();
            if (!process(pullRequest.getTitle())) {
                comment.append("This pull request has wrong title \n");
            }
            try {
                validatePullRequestCommitTitles(owner, repository, pullRequest.getNumber(), comment);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (comment.length() > 0) {
                try {
                    addIssueComment(owner, repository, pullRequest.getNumber(), comment.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void validatePullRequestCommitTitles(String owner,
                                                String repository,
                                                Long pullRequestId,
                                                final StringBuilder comment) throws IOException {
        getAllCommits(owner, repository, pullRequestId).forEach(commitInfo -> {
            if (!process(commitInfo.getCommit().getMessage())) {
                comment.append(String.format("Commit with sha %s has wrong title \n", commitInfo.getCommit().getSha()));
            }
        });
    }
}