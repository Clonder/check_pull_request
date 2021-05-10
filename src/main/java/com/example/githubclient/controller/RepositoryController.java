package com.example.githubclient.controller;

import com.example.githubclient.service.RepositoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("repository")
public class RepositoryController {
    private final RepositoryService service;


    public RepositoryController(RepositoryService service) {
        this.service = service;
    }

    @PostMapping
    public void addRepository(@RequestParam final String repositoryName) {


    }
}
