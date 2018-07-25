package ru.mediasoft.unipolls.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;
import ru.mediasoft.unipolls.dataclass.MySurveysModel;
import ru.mediasoft.unipolls.dataclass.UserInfoModel;

public interface UserInfoApi {

    @Headers("Authorization: Bearer LBiQHvyhTbN3YqEM1ItHOjefSF2SinkRVKaPaJcRG7wRtyt0E9rww1BSTLBnuN5zysaxkemzk.ydEqZNqiXlrzBLMBK-wNurxQpoagNoto6xFL.KRakvJePVtmB1SAHz")
    @GET
    Single<UserInfoModel> getUserInfo(@Url String url);

    @Headers("Authorization: Bearer LBiQHvyhTbN3YqEM1ItHOjefSF2SinkRVKaPaJcRG7wRtyt0E9rww1BSTLBnuN5zysaxkemzk.ydEqZNqiXlrzBLMBK-wNurxQpoagNoto6xFL.KRakvJePVtmB1SAHz")
    @GET
    Single<MySurveysModel> getMySurveys(@Url String url);

    String BASE_URL = "https://api.surveymonkey.com/v3/";


}
