package ru.mediasoft.unipolls;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkService {

    final private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(UserInfoApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    final public UserInfoApi userInfoApi = retrofit.create(UserInfoApi.class);
}
