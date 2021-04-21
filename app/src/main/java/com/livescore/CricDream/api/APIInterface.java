package com.livescore.CricDream.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("auth/")
    Call<JsonObject> getAccessToken(@Query("access_key") String access_key, @Query("secret_key") String secret_key, @Query("app_id") String app_id, @Query("device_id") String device_id);
}