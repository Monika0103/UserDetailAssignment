package com.userdetail.demo.userrepository;

import androidx.lifecycle.MutableLiveData;

import com.userdetail.demo.apis.UserDetailDataService;
import com.userdetail.demo.constants.GlobalConstants;
import com.userdetail.demo.model.UserDetailBean;

import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDataRepository {

    private UserDetailDataService userDetailDataService;
    private MutableLiveData<UserDetailBean> userDetailBeanMutableLiveData;

    /**
     * Initialize Retrofit and OkHttp
     */
    public UserDataRepository() {
        userDetailBeanMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        userDetailDataService = new retrofit2.Retrofit.Builder()
                .baseUrl(GlobalConstants.USER_DATA_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserDetailDataService.class);

    }

    /**
     * Get User Data Using API
     */
    public void getUserDetailData(int pageNo) {
        userDetailDataService.getUserData(""+pageNo)
                .enqueue(new Callback<UserDetailBean>() {
                    @Override
                    public void onResponse(Call<UserDetailBean> call, Response<UserDetailBean> response) {
                        if (response.body() != null) {
                            userDetailBeanMutableLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailBean> call, Throwable t) {
                        userDetailBeanMutableLiveData.postValue(null);
                    }
                });
    }

    /**
     * Notify User Data to Observer
     */
    public MutableLiveData<UserDetailBean> getUserResponseLiveData() {
        return userDetailBeanMutableLiveData;
    }
}
