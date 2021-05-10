package com.example.githubclient.config;

import com.example.githubclient.GithubApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * For creation Retrofit's clients.
 */
@Configuration
public class RetrofitBeans {

    static String API_BASE_URL = "https://api.github.com/";

    @Bean
    public GithubApiInterface getGithubApiInterface() {
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

        return retrofit.create(GithubApiInterface.class);
    }
}
