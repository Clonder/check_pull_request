package com.example.githubclient;

import com.example.githubclient.model.GithubUser;
import com.example.githubclient.model.Repository;
import com.example.githubclient.repository.RepositoryRepository;
import com.example.githubclient.service.GithubClient;
import com.example.githubclient.service.RepositoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.example.githubclient")
public class RepositoryServiceTests {

    @Autowired
    private RepositoryService repositoryService;

    @MockBean
    private GithubClient githubClient;

    @MockBean
    private RepositoryRepository repositoryRepository;

    @Test
    public void checkAddRepository_Error() {
        Mockito.doReturn(false).when(githubClient).isUserRepositoryExist(any(), any());
        repositoryService.addRepository(new GithubUser(), "");
        Mockito.verify(repositoryRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void checkAddRepository_Success() {
        Mockito.doReturn(true).when(githubClient).isUserRepositoryExist(any(), any());
        repositoryService.addRepository(new GithubUser(), "");
        Mockito.verify(repositoryRepository).save(Mockito.any());
    }

    @Test
    public void getAll_Success() {
        var repositories = List.of(new Repository());
        Mockito.doReturn(repositories).when(repositoryRepository).findAll();
        Assertions.assertIterableEquals(repositories, repositoryService.getAll());
    }
}
