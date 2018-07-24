package ru.mediasoft.unipolls;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface UserInfoApi {
    @Headers("Authorization: Bearer LBiQHvyhTbN3YqEM1ItHOjefSF2SinkRVKaPaJcRG7wRtyt0E9rww1BSTLBnuN5zysaxkemzk.ydEqZNqiXlrzBLMBK-wNurxQpoagNoto6xFL.KRakvJePVtmB1SAHz")
    @GET
    Single<UserInfoModel> getUserInfo(@Url String url);

    String BASE_URL = "https://api.surveymonkey.com/v3/";
}
