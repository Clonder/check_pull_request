package com.example.githubclient;

import com.example.githubclient.model.CommentResponse;
import com.example.githubclient.model.CommitInfo;
import com.example.githubclient.model.PullRequestInfo;
import com.example.githubclient.model.PullRequestState;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class GitHubClientController {
    private final GithubClient githubService;

    @GetMapping("all-pulls/{owner}/{repository}")
    public List<PullRequestInfo> getAllPulls(@PathVariable final String owner, @PathVariable final String repository,
                                             @RequestParam PullRequestState state)
            throws IOException {

        return githubService.getAllPulls(owner, repository, state);
    }

    @GetMapping("all-commits/{owner}/{repository}/{pullNumber}")
    public List<CommitInfo> getAllCommits(@PathVariable final String owner, @PathVariable final String repository,
                                          @PathVariable Long pullNumber) throws IOException {

        return githubService.getAllCommits(owner, repository, pullNumber);
    }

    @GetMapping("all-issue-comment/{owner}/{repository}/{issueNumber}")
    public List<CommentResponse> getAllIssueComments(@PathVariable final String owner, @PathVariable final String repository,
                                                     @PathVariable Long issueNumber) throws IOException {

        return githubService.getAllIssueComments(owner, repository, issueNumber);
    }

    @GetMapping("all-review-comment/{owner}/{repository}/{pullNumber}")
    public List<CommentResponse> getAllReviewComments(@PathVariable final String owner, @PathVariable final String repository,
                                                      @PathVariable Long pullNumber)
            throws IOException {

        return githubService.getAllReviewComments(owner, repository, pullNumber);
    }

    @GetMapping("add-review-comment/{owner}/{repository}/{pullNumber}")
    public void addReviewComment(@PathVariable final String owner, @PathVariable final String repository,
                                 @PathVariable Long pullNumber, @RequestParam(name = "commit_id") String commitId,
                                 @RequestParam String comment, @RequestParam String path, @RequestParam Integer position)
            throws IOException {

        githubService.addReviewComment(owner, repository, pullNumber, commitId, comment, position, path);
    }

    @GetMapping("add-issue-comment/{owner}/{repository}/{issueNumber}")
    public void addIssueComment(@PathVariable final String owner, @PathVariable final String repository,
                                @PathVariable Long issueNumber, @RequestParam String comment)
            throws IOException {

        githubService.addIssueComment(owner, repository, issueNumber, comment);
    }

}
