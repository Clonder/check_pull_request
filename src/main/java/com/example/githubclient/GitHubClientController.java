package com.example.githubclient;

import com.example.githubclient.model.CommentResponse;
import com.example.githubclient.model.CommitInfo;
import com.example.githubclient.model.PullRequestInfo;
import com.example.githubclient.model.PullRequestState;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class GitHubClientController {
    private final GithubClient githubService;

    @GetMapping("all-pulls")
    public List<PullRequestInfo> getAllPulls(@RequestParam final String owner, @RequestParam final String repository,
                                             @RequestParam PullRequestState state)
            throws IOException {

        return githubService.getAllPulls(owner, repository, state);
    }

    @GetMapping("all-commits")
    public List<CommitInfo> getAllCommits(@RequestParam final String owner, @RequestParam final String repository,
                                          @RequestParam Long pullNumber) throws IOException {

        return githubService.getAllCommits(owner, repository, pullNumber);
    }

    @GetMapping("all-issue-comment")
    public List<CommentResponse> getAllIssueComments(@RequestParam final String owner, @RequestParam final String repository,
                                                     @RequestParam Long issueNumber) throws IOException {

        return githubService.getAllIssueComments(owner, repository, issueNumber);
    }

    @GetMapping("all-review-comment")
    public List<CommentResponse> getAllReviewComments(@RequestParam final String owner, @RequestParam final String repository,
                                                      @RequestParam Long pullNumber)
            throws IOException {

        return githubService.getAllReviewComments(owner, repository, pullNumber);
    }

    @GetMapping("add-review-comment")
    public void addReviewComment(@RequestParam final String owner, @RequestParam final String repository,
                                 @RequestParam Long pullNumber, @RequestParam(name = "commit_id") String commitId,
                                 @RequestParam String comment, @RequestParam String path, @RequestParam Integer position)
            throws IOException {

        githubService.addReviewComment(owner, repository, pullNumber, commitId, comment, position, path);
    }

    @GetMapping("add-issue-comment")
    public void addIssueComment(@RequestParam final String owner, @RequestParam final String repository,
                                @RequestParam Long issueNumber, @RequestParam String comment)
            throws IOException {

        githubService.addIssueComment(owner, repository, issueNumber, comment);
    }

}
