package ru.mediasoft.unipolls;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface SurveyMonkeyApi {
    @Headers({"Authorization:bearer LBiQHvyhTbN3YqEM1ItHOjefSF2SinkRVKaPaJcRG7wRtyt0E9rww1BSTLBnuN5zysaxkemzk.ydEqZNqiXlrzBLMBK-wNurxQpoagNoto6xFL.KRakvJePVtmB1SAHz"})
    @GET("/v3/surveys/")
    Single<SearchResult> getSurveys();

    @Headers({"Authorization:bearer LBiQHvyhTbN3YqEM1ItHOjefSF2SinkRVKaPaJcRG7wRtyt0E9rww1BSTLBnuN5zysaxkemzk.ydEqZNqiXlrzBLMBK-wNurxQpoagNoto6xFL.KRakvJePVtmB1SAHz"})
    @GET("/v3/surveys/{id}/responses")
    Single<SearchResult> getResponseCount(@Path("id") String id);
}
