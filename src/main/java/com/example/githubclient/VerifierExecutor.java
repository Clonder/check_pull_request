package com.example.githubclient;


import com.example.githubclient.model.github.PullRequestState;
import com.example.githubclient.service.GithubClient;
import com.example.githubclient.service.RepositoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VerifierExecutor {

    private final GithubClient githubClient;
    private final RepositoryService repositoryService;

    public VerifierExecutor(GithubClient githubClient,
                            RepositoryService repositoryService) {
        this.githubClient = githubClient;
        this.repositoryService = repositoryService;
    }

    @Scheduled(cron = "0 */4 * * * *")
    public void verify() {
        repositoryService.getAll()
            .forEach(repository -> {
                    try {
                        githubClient.validatePullRequest(
                            repository.getOwner().getUsername(),
                            repository.getName(),
                            PullRequestState.ALL
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            );
    }
}
