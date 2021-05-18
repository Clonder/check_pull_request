package com.example.githubclient.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GithubUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Automatic generation Id
    private long id;
    @Column(unique = true)
    private String username;


    public GithubUser() {
    }

    public GithubUser(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String ownerName) {
        this.username = ownerName;
    }
}
