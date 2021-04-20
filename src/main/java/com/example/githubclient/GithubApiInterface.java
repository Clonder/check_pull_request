package com.example.githubclient;

import com.example.githubclient.model.CommitInfo;
import com.example.githubclient.model.PullRequestInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface GithubApiInterface {
    @GET("/repos/{owner}/{repo}/pulls")
    Call<List<PullRequestInfo>> getAllPulls(@Header("Authorization") String accessToken,
                                            @Header("Accept") String apiVersionSpec,
                                            @Path("repo") String repo, @Path("owner") String owner);

    @GET("/repos/{owner}/{repo}/pulls/{pull_number}/commits")
    Call<List<CommitInfo>> getAllCommitsForPull(@Header("Authorization") String accessToken,
                                                @Header("Accept") String apiVersionSpec,
                                                @Path("repo") String repo, @Path("owner") String owner,
                                                @Path("pull_number") Long pullNumber);

    @POST("/repos/{owner}/{repo}/pulls/{pull_number}/comments")
    Call<ResponseBody> addComment(@Header("Authorization") String accessToken,
                                  @Header("Accept") String apiVersionSpec,
                                  @Path("repo") String repo,
                                  @Path("owner") String owner,
                                  @Path("pull_number") Long pullNumber,
                                  @Body String body);
}