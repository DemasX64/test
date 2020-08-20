package ru.test.test;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JSONPlaceholderApi {
    @POST("/jokes/random/{count}")
    public Call<String> getJokes(@Path("count") int count);
}