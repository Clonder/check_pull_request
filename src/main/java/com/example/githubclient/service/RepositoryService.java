package com.example.githubclient.service;

import com.example.githubclient.model.GithubUser;
import com.example.githubclient.model.Repository;
import com.example.githubclient.repository.GithubUserRepository;
import com.example.githubclient.repository.RepositoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class RepositoryService {
    private final RepositoryRepository repository;
    private final GithubClient githubClient;
    private final GithubUserRepository githubUserRepository;

    public RepositoryService(RepositoryRepository repository,
                             GithubClient githubClient,
                             final GithubUserRepository githubUserRepository) {
        this.repository = repository;
        this.githubClient = githubClient;
        this.githubUserRepository = githubUserRepository;
    }

    /**
     * Function overload.
     */
    public void addRepository(final String username, final String repositoryName) {
        addRepository(githubUserRepository.findByUsername(username), repositoryName);
    }

    public void addRepository(final GithubUser owner, final String repositoryName) {
        if (githubClient.isUserRepositoryExist(owner.getUsername(), repositoryName)) {
            repository.save(new Repository(owner, repositoryName));
        } else {
            log.error("Repository for user " + owner + " not exist: " + repositoryName);
        }
    }

    public List<Repository> getAll() {
        return repository.findAll();
    }
}
