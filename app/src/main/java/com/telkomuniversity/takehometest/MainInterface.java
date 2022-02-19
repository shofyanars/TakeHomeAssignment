package com.telkomuniversity.takehometest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MainInterface {

    @GET("api/users?page=1")
    Call<ListUserResponse> getList();

}
