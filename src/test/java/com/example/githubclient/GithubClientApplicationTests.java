package com.example.githubclient;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.githubclient.MessageTemplateVerifier.process;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GithubClientApplicationTests {

    @Test
    public void contextLoads() {
    }

    @ParameterizedTest
    @CsvSource({"GENERATOR,1021,Added", "LEETCODE,1022,Deleted", "GENERATOR,1013,Refactored",
            "LEETCODE,2021,Moved", "LEETCODE,2022,Fixed"})
    public void process_Valid(String prefix, String groupNumber, String action) {
        Assertions.assertTrue(process(prefix + " " + groupNumber + " " + action));
    }

    @ParameterizedTest
    @CsvSource({"GENERATOR,1000,Added", "LEETCODE,1022,Void", "Generator,1013,Refactored",
            "LEET,2021,Moved", "LEETCODE,2022,Faxed"})
    public void process_Invalid(String prefix, String groupNumber, String action) {
        Assertions.assertFalse(process(prefix + " " + groupNumber + " " + action));
    }

}
