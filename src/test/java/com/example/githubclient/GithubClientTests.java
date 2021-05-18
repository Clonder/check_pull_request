package com.example.githubclient;

import com.example.githubclient.model.github.CommentResponse;
import com.example.githubclient.model.github.CommitBody;
import com.example.githubclient.model.github.CommitInfo;
import com.example.githubclient.model.github.PullRequestInfo;
import com.example.githubclient.model.github.PullRequestState;
import com.example.githubclient.service.GithubClient;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import retrofit2.mock.Calls;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GithubClientTests {

    @MockBean
    private GithubApiInterface githubApi;

    @SpyBean
    private GithubClient githubClient;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void checkUserExistence_Success() {
        doReturn(Calls.response(new Object())).when(githubApi).getUser(any(), any(), any());
        Assertions.assertTrue(githubClient.isUserExist(""));
    }

    @Test
    public void checkUserExistence_Error() {
        doReturn(Calls.failure(new IOException())).when(githubApi).getUser(any(), any(), any());
        Assertions.assertFalse(githubClient.isUserExist(""));
    }

    @Test
    public void checkUserRepositoryExistence_Success() {
        doReturn(Calls.response(new Object())).when(githubApi).getRepository(any(), any(), any(), any());
        Assertions.assertTrue(githubClient.isUserRepositoryExist("", ""));
    }

    @Test
    public void checkUserRepositoryExistence_Error() {
        doReturn(Calls.failure(new IOException())).when(githubApi).getRepository(any(), any(), any(), any());
        Assertions.assertFalse(githubClient.isUserRepositoryExist("", ""));
    }

    @Test
    public void checkGetAllPulls_Error() {
        doReturn(Calls.failure(new IOException())).when(githubApi).getAllPulls(any(), any(), any(), any(), any());
        Assertions.assertThrows(IOException.class, () -> githubClient.getAllPulls("", "", PullRequestState.ALL));
    }

    @Test
    public void checkGetAllPulls_Success() throws IOException {
        var response = List.of(new PullRequestInfo());
        doReturn(Calls.response(response)).when(githubApi).getAllPulls(any(), any(), any(), any(), any());
        Assertions.assertIterableEquals(response,
            githubClient.getAllPulls("", "", PullRequestState.ALL));
    }

    @Test
    public void checkGetAllCommits_Error() {
        doReturn(Calls.failure(new IOException())).when(githubApi)
            .getAllCommitsForPull(any(), any(), any(), any(), any());
        Assertions.assertThrows(IOException.class,
            () -> githubClient.getAllCommits("", "", 15L));
    }

    @Test
    public void checkGetAllCommits_Success() throws IOException {
        var response = List.of(new CommitInfo());
        doReturn(Calls.response(response)).when(githubApi)
            .getAllCommitsForPull(any(), any(), any(), any(), any());
        Assertions.assertIterableEquals(response,
            githubClient.getAllCommits("", "", 15L));
    }

    @Test
    public void checkGetAllIssueComments_Error() {
        doReturn(Calls.failure(new IOException())).when(githubApi)
            .getAllIssueCommentsForPull(any(), any(), any(), any(), any());
        Assertions.assertThrows(IOException.class,
            () -> githubClient.getAllIssueComments("", "", 15L));
    }

    @Test
    public void checkGetAllIssueComments_Success() throws IOException {
        var response = List.of(new CommentResponse());
        doReturn(Calls.response(response)).when(githubApi)
            .getAllIssueCommentsForPull(any(), any(), any(), any(), any());
        Assertions.assertIterableEquals(response,
            githubClient.getAllIssueComments("", "", 15L));
    }

    @Test
    public void checkGetAllReviewComments_Error() {
        doReturn(Calls.failure(new IOException())).when(githubApi)
            .getAllReviewCommentsForPull(any(), any(), any(), any(), any());
        Assertions.assertThrows(IOException.class,
            () -> githubClient.getAllReviewComments("", "", 15L));
    }

    @Test
    public void checkGetAllReviewComments_Success() throws IOException {
        var response = List.of(new CommentResponse());
        doReturn(Calls.response(response)).when(githubApi)
            .getAllReviewCommentsForPull(any(), any(), any(), any(), any());
        Assertions.assertIterableEquals(response,
            githubClient.getAllReviewComments("", "", 15L));
    }

    @Test
    public void checkAddReviewComment_Error() {
        doReturn(Calls.failure(new IOException())).when(githubApi)
            .addReviewComment(any(), any(), any(), any(), any(), any());
        Assertions.assertThrows(IOException.class,
            () -> githubClient.addReviewComment("", "", 15L, "", "", -1, ""));
    }

    @Test
    public void checkAddReviewComment_Success() {
        doReturn(Calls.response(new Object())).when(githubApi)
            .addReviewComment(any(), any(), any(), any(), any(), any());
        Assertions.assertDoesNotThrow(
            () -> githubClient.addReviewComment("", "", 15L, "", "", -1, ""));
    }

    @Test
    public void checkAddIssueComment_Error() {
        doReturn(Calls.failure(new IOException())).when(githubApi)
            .addIssueComment(any(), any(), any(), any(), any(), any());
        Assertions.assertThrows(IOException.class,
            () -> githubClient.addIssueComment("", "", 15L, ""));
    }

    @Test
    public void checkAddIssueComment_Success() {
        doReturn(Calls.response(new Object())).when(githubApi)
            .addIssueComment(any(), any(), any(), any(), any(), any());
        Assertions.assertDoesNotThrow(
            () -> githubClient.addIssueComment("", "", 15L, ""));
    }

    @Test
    public void checkValidatePullRequestCommitTitles_InvalidCommitTitle() throws IOException {
        var comment = new StringBuilder();
        doReturn(List.of(new CommitInfo(new CommitBody("Foo", "13d2fwfeds")))).when(githubClient)
            .getAllCommits(any(), any(), any());
        githubClient.validatePullRequestCommitTitles("", "", 15L, comment);
        Assertions.assertTrue(comment.length() > 0);
    }

    @Test
    public void checkValidatePullRequestCommitTitles_ValidCommitTitle() throws IOException {
        var comment = new StringBuilder();
        doReturn(List.of(new CommitInfo(new CommitBody("LEETCODE 1022 Deleted", "13d2fwfeds")))).when(githubClient)
            .getAllCommits(any(), any(), any());
        githubClient.validatePullRequestCommitTitles("", "", 15L, comment);
        Assertions.assertEquals(0, comment.length());
    }

    @Test
    public void checkValidatePullRequest_InvalidPullRequestTitleAndCommitTitle() throws IOException {
        var response = List.of(new PullRequestInfo(""));

        doReturn(response).when(githubClient).getAllPulls(any(), any(), any());
        doReturn(List.of(new CommitInfo(new CommitBody("Foo", "13d2fwfeds")))).when(githubClient)
            .getAllCommits(any(), any(), any());
        doNothing().when(githubClient).addIssueComment(any(), any(), any(), any());

        githubClient.validatePullRequest("", "", PullRequestState.ALL);
        Mockito.verify(githubClient).getAllPulls(any(), any(), any());
        Mockito.verify(githubClient).validatePullRequestCommitTitles(any(), any(), any(), any());
        Mockito.verify(githubClient).addIssueComment(any(), any(), any(), any());
    }

    @Test
    public void checkValidatePullRequest_ValidPullRequestTitleAndInvalidCommitTitle() throws IOException {
        var response = List.of(new PullRequestInfo("LEETCODE 1022 Deleted"));

        doReturn(response).when(githubClient).getAllPulls(any(), any(), any());
        doReturn(List.of(new CommitInfo(new CommitBody("Foo", "13d2fwfeds")))).when(githubClient)
            .getAllCommits(any(), any(), any());
        doNothing().when(githubClient).addIssueComment(any(), any(), any(), any());

        githubClient.validatePullRequest("", "", PullRequestState.ALL);
        Mockito.verify(githubClient).getAllPulls(any(), any(), any());
        Mockito.verify(githubClient).validatePullRequestCommitTitles(any(), any(), any(), any());
        Mockito.verify(githubClient).addIssueComment(any(), any(), any(), any());
    }

    @Test
    public void checkValidatePullRequest_ValidPullRequestTitleAndValidCommitTitle() throws IOException {
        var response = List.of(new PullRequestInfo("LEETCODE 1022 Deleted"));

        doReturn(response).when(githubClient).getAllPulls(any(), any(), any());
        doReturn(List.of(new CommitInfo(new CommitBody("LEETCODE 1022 Deleted", "13d2fwfeds")))).when(githubClient)
            .getAllCommits(any(), any(), any());

        githubClient.validatePullRequest("", "", PullRequestState.ALL);
        Mockito.verify(githubClient).getAllPulls(any(), any(), any());
        Mockito.verify(githubClient).validatePullRequestCommitTitles(any(), any(), any(), any());
        Mockito.verify(githubClient, never()).addIssueComment(any(), any(), any(), any());
    }

    @Test
    public void checkValidatePullRequest_InvalidPullRequestTitleAndValidCommitTitle() throws IOException {
        var response = List.of(new PullRequestInfo(""));

        doReturn(response).when(githubClient).getAllPulls(any(), any(), any());
        doReturn(List.of(new CommitInfo(new CommitBody("LEETCODE 1022 Deleted", "13d2fwfeds")))).when(githubClient)
            .getAllCommits(any(), any(), any());
        doNothing().when(githubClient).addIssueComment(any(), any(), any(), any());

        githubClient.validatePullRequest("", "", PullRequestState.ALL);
        Mockito.verify(githubClient).getAllPulls(any(), any(), any());
        Mockito.verify(githubClient).validatePullRequestCommitTitles(any(), any(), any(), any());
        Mockito.verify(githubClient).addIssueComment(any(), any(), any(), any());
    }
}
