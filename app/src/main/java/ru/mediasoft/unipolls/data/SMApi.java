package ru.mediasoft.unipolls.data;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyModel;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyRequestModel;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;
import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;
import ru.mediasoft.unipolls.domain.dataclass.userinfo.UserInfoModel;
import ru.mediasoft.unipolls.other.Constants;


public interface SMApi {

    @Headers({Constants.SurveyMonkeyApi.AUTH_KEY, Constants.SurveyMonkeyApi.CONTENT_TYPE})
    @GET("users/me")
    Single<UserInfoModel> getUserInfo();

    @Headers({Constants.SurveyMonkeyApi.AUTH_KEY, Constants.SurveyMonkeyApi.CONTENT_TYPE})
    @POST("surveys")
    Single<CreateSurveyModel> createSurvey(@Body CreateSurveyRequestModel request);

    @Headers({Constants.SurveyMonkeyApi.AUTH_KEY})
    @GET("surveys")
    Single<SearchResultSurveys> getSurveys();

    @Headers({Constants.SurveyMonkeyApi.AUTH_KEY})
    @GET("/v3/surveys/{id}/details")
    Single<SearchResultDetails> getSurveyDetails(@Path("id") String id);
}
