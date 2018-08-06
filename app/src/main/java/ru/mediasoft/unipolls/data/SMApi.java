package ru.mediasoft.unipolls.data;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyModel;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyRequestModel;
import ru.mediasoft.unipolls.domain.dataclass.GetAccessTokenModel;
import ru.mediasoft.unipolls.domain.dataclass.polldetails.SearchResultDetails;
import ru.mediasoft.unipolls.domain.dataclass.polllist.SearchResultSurveys;
import ru.mediasoft.unipolls.domain.dataclass.userinfo.UserInfoModel;
import ru.mediasoft.unipolls.other.Constants;


public interface SMApi {

    @Headers(Constants.SurveyMonkeyApi.CONTENT_TYPE)
    @GET("v3/users/me")
    Single<UserInfoModel> getUserInfo(@Header("Authorization") String token);

    @Headers(Constants.SurveyMonkeyApi.CONTENT_TYPE)
    @POST("v3/surveys")
    Single<CreateSurveyModel> createSurvey(@Header("Authorization") String token, @Body CreateSurveyRequestModel request);

    @GET("v3/surveys")
    Single<SearchResultSurveys> getSurveys(@Header("Authorization") String token);

    @GET("/v3/surveys/{id}/details")
    Single<SearchResultDetails> getSurveyDetails(@Header("Authorization") String token, @Path("id") String id);

    @FormUrlEncoded
    @POST("oauth/token")
    Single<GetAccessTokenModel> getAccessToken(@Field("client_secret") String client_secret,
                                               @Field("code") String code,
                                               @Field("redirect_uri") String redirect_uri,
                                               @Field("client_id") String client_id,
                                               @Field("grant_type") String grant_type);
}
