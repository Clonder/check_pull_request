package com.example.githubclient.repository;

import com.example.githubclient.model.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubUserRepository extends JpaRepository<GithubUser, Long> {
    GithubUser findByUsername(String username);
}
