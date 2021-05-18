package com.example.githubclient;

import com.example.githubclient.dto.UserRepositoriesDto;
import com.example.githubclient.service.GithubUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Log4j2
@Component
public class SetupHandler implements ApplicationRunner {
    private final GithubUserService userService;

    public SetupHandler(GithubUserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(false);
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args.getSourceArgs());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("File path", options);
            System.exit(1);
        }
        String inputFilePath = cmd.getOptionValue("input");
        System.out.println(inputFilePath);

        if (userService.isDatabaseEmpty() && inputFilePath == null) {
            log.error("Database empty and data file not provided!");
        } else if (inputFilePath != null) {
            var inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                throw new FileNotFoundException(inputFilePath);
            }
            userService.addUserWithRepositories(Objects.requireNonNull(readData(inputFile)));
        }
    }

    private UserRepositoriesDto[] readData(final File inputFile) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(inputFile, UserRepositoriesDto[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
