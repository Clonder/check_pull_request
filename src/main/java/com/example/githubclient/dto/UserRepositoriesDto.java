package com.example.githubclient.dto;

import lombok.ToString;

import java.util.List;

@ToString
public class UserRepositoriesDto {
    private String owner;

    private List<String> ownerRepositories;

    public UserRepositoriesDto() {
    }

    public UserRepositoriesDto(String owner, List<String> ownerRepositories) {
        this.owner = owner;
        this.ownerRepositories = ownerRepositories;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getOwnerRepositories() {
        return ownerRepositories;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setOwnerRepositories(List<String> ownerRepositories) {
        this.ownerRepositories = ownerRepositories;
    }
}
