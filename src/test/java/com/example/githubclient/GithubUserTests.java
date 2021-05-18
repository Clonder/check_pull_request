package com.example.githubclient;

import com.example.githubclient.dto.UserRepositoriesDto;
import com.example.githubclient.model.GithubUser;
import com.example.githubclient.repository.GithubUserRepository;
import com.example.githubclient.repository.RepositoryRepository;
import com.example.githubclient.service.GithubClient;
import com.example.githubclient.service.GithubUserService;
import com.example.githubclient.service.RepositoryService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.example.githubclient")
public class GithubUserTests {

    private static final String NOT_EXIST_USERNAME = "*01sdfsf";
    private static final String EXIST_USERNAME = "Clonder";

    @SpyBean
    private GithubClient githubClient;

    @SpyBean
    private RepositoryService repositoryService;

    @MockBean
    private RepositoryRepository repositoryRepository;

    @Autowired
    private GithubUserService service;

    @Autowired
    private GithubUserRepository githubUserRepository;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void beforeEachTest() {
        githubUserRepository.deleteAll();
    }

    @Test
    public void addUser_NotExist() {
        Assertions.assertFalse(service.addUser(NOT_EXIST_USERNAME));
    }

    @Test
    public void addUser_Exist() {
        Assertions.assertTrue(service.addUser(EXIST_USERNAME));
    }

    @Test
    public void addUserWithRepositories_NotExistUser() {
        var spyService = spy(service);
        spyService.addUserWithRepositories(
            new UserRepositoriesDto[]{new UserRepositoriesDto(NOT_EXIST_USERNAME, List.of("111"))});
        Mockito.verify(spyService).addUser(NOT_EXIST_USERNAME);
        Mockito.verify(repositoryService, never()).addRepository(NOT_EXIST_USERNAME, "111");
    }

    @Test
    public void addUserWithRepositories_ExistUserNotExistRepository() {
        var spyService = spy(service);
        spyService.addUserWithRepositories(
            new UserRepositoriesDto[]{new UserRepositoriesDto(EXIST_USERNAME, List.of("11111"))});
        Mockito.verify(githubClient).isUserRepositoryExist(EXIST_USERNAME, "11111");
        Mockito.verify(spyService).addUser(EXIST_USERNAME);
        Mockito.verify(repositoryService)
            .addRepository(any(GithubUser.class), Mockito.eq("11111"));
        Mockito.verify(repositoryRepository, never()).save(any());

    }

    @Test
    public void addUserWithRepositories_ExistUserExistRepository() {
        var spyService = spy(service);
        spyService.addUserWithRepositories(
            new UserRepositoriesDto[]{new UserRepositoriesDto(EXIST_USERNAME, List.of("check_pull_request"))});
        Mockito.verify(spyService).addUser(EXIST_USERNAME);
        Mockito.verify(repositoryService)
            .addRepository(any(GithubUser.class), Mockito.eq("check_pull_request"));
        Mockito.verify(repositoryRepository).save(any());
    }

    @Test
    public void checkIsDatabaseEmpty_Empty() {
        Assertions.assertTrue(service.isDatabaseEmpty());
    }

    @Test
    public void checkIsDatabaseEmpty_NotEmpty() {
        service.addUser("Varka");
        Assertions.assertFalse(service.isDatabaseEmpty());
    }
}
