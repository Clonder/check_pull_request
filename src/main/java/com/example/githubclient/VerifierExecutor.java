package com.example.githubclient;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VerifierExecutor {


//    @Scheduled(cron= "0 * * ? * *")
//    public void verify() throws IOException{
//            githubClient.addComment("Clonder", "java_au", 25L, "Oops!");
//        }

    @Scheduled(cron = "*/5 * * * * *")
    public void print() {
        System.out.println("Влад Котов, где ТЗ? Ты же обещал");
    }

}
