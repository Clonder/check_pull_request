package com.example.githubclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GitHubClientController {
    @Autowired
    private GithubClient githubService;

}
