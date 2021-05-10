package com.example.githubclient.controller;

import com.example.githubclient.dto.UserRepositoriesDto;
import com.example.githubclient.service.GithubUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class GithubUserController {

    private final GithubUserService service;

    public GithubUserController(GithubUserService service) {
        this.service = service;
    }

    /**
     * Adding user to database.
     */
    @PostMapping
    public void addUser(@RequestParam final String username) {
        service.addUser(username);
    }

    /**
     * Adding user and his list of repositories to database from file.
     */
    @PostMapping("with-repositories")
    public void addUserWithRepositories(@RequestBody final UserRepositoriesDto[] users) {
        service.addUserWithRepositories(users);
    }

}
