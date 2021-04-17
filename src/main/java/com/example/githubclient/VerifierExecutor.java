package com.example.githubclient;


import com.example.githubclient.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VerifierExecutor {

    @Autowired
    private GitHubService gitHubService;


//    @Scheduled(cron= "0 * * ? * *")
//    public void verify() throws IOException{
//            gitHubService.createComment();
//        }

    @Scheduled(cron= "* * * ? * *")
    public void print() throws IOException{
        System.out.println("Hello");
    }

}
