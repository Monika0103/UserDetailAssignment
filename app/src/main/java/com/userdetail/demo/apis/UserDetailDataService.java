package com.userdetail.demo.apis;



import com.userdetail.demo.model.UserDetailBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface UserDetailDataService {
    //https://gorest.co.in/public-api/users
    @GET("/public-api/users")
    Call<UserDetailBean> getUserData(
            @Query("page") String pageNo);
}
