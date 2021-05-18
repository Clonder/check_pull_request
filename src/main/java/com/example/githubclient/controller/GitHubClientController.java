package com.example.githubclient.controller;

import com.example.githubclient.model.github.CommentResponse;
import com.example.githubclient.model.github.CommitInfo;
import com.example.githubclient.model.github.PullRequestInfo;
import com.example.githubclient.model.github.PullRequestState;
import com.example.githubclient.service.GithubClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("github")
@RestController
public class GitHubClientController {
    private final GithubClient githubService;

    public GitHubClientController(GithubClient githubService) {
        this.githubService = githubService;
    }

    @GetMapping("{owner}/{repository}/all-pulls")
    public List<PullRequestInfo> getAllPulls(@PathVariable final String owner,
                                             @PathVariable final String repository,
                                             @RequestParam PullRequestState state)
            throws IOException {

        return githubService.getAllPulls(owner, repository, state);
    }

    @GetMapping("{owner}/{repository}/{pullNumber}/all-commits")
    public List<CommitInfo> getAllCommits(@PathVariable final String owner,
                                          @PathVariable final String repository,
                                          @PathVariable Long pullNumber)
            throws IOException {

        return githubService.getAllCommits(owner, repository, pullNumber);
    }

    @GetMapping("{owner}/{repository}/{issueNumber}/all-issue-comment")
    public List<CommentResponse> getAllIssueComments(@PathVariable final String owner,
                                                     @PathVariable final String repository,
                                                     @PathVariable Long issueNumber)
            throws IOException {

        return githubService.getAllIssueComments(owner, repository, issueNumber);
    }

    @GetMapping("{owner}/{repository}/{pullNumber}/all-review-comment")
    public List<CommentResponse> getAllReviewComments(@PathVariable final String owner,
                                                      @PathVariable final String repository,
                                                      @PathVariable Long pullNumber)
            throws IOException {

        return githubService.getAllReviewComments(owner, repository, pullNumber);
    }

    @GetMapping("{owner}/{repository}/{pullNumber}/add-review-comment")
    public void addReviewComment(@PathVariable final String owner,
                                 @PathVariable final String repository,
                                 @PathVariable Long pullNumber,
                                 @RequestParam(name = "commit_id") String commitId,
                                 @RequestParam String comment,
                                 @RequestParam String path,
                                 @RequestParam Integer position)
            throws IOException {

        githubService.addReviewComment(owner, repository, pullNumber, commitId, comment, position, path);
    }

    @GetMapping("{owner}/{repository}/{issueNumber}/add-issue-comment")
    public void addIssueComment(@PathVariable final String owner,
                                @PathVariable final String repository,
                                @PathVariable Long issueNumber,
                                @RequestParam String comment)
            throws IOException {

        githubService.addIssueComment(owner, repository, issueNumber, comment);
    }

}
