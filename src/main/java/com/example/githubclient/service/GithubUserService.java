package com.example.githubclient.service;

import com.example.githubclient.dto.UserRepositoriesDto;
import com.example.githubclient.model.GithubUser;
import com.example.githubclient.repository.GithubUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Log4j2
@Service
public class GithubUserService {
    private final GithubUserRepository repository;
    private final RepositoryService repositoryService;
    private final GithubClient githubClient;

    public GithubUserService(GithubUserRepository repository,
                             RepositoryService repositoryService,
                             GithubClient githubClient) {
        this.repository = repository;
        this.repositoryService = repositoryService;
        this.githubClient = githubClient;
    }

    public boolean addUser(final String username) {
        if (githubClient.isUserExist(username)) {
            repository.saveAndFlush(new GithubUser(username));
            return true;
        } else {
            log.error("User not exist: " + username);
            return false;
        }
    }

    public void addUserWithRepositories(final UserRepositoriesDto[] users) {
        Arrays.stream(users).forEach(user -> {
            if (addUser(user.getOwner())) {
                var databaseUser = repository.findByUsername(user.getOwner());
                user.getOwnerRepositories().forEach(repositoryName ->
                    repositoryService.addRepository(databaseUser, repositoryName));
            }
        });
    }

    public boolean isDatabaseEmpty() {
        return repository.count() == 0;
    }
}
