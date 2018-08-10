package ru.mediasoft.unipolls.data.net;

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
import ru.mediasoft.unipolls.domain.dataclass.pollpages.SearchResultPages;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.SearchResultQuestionDetails;
import ru.mediasoft.unipolls.domain.dataclass.pollquestions.SearchResultQuestions;
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

    
    //---------------------------------------

    @Headers({Constants.SurveyMonkeyApi.AUTH_KEY})
    @GET("surveys/{survey_id}/pages")
    Single<SearchResultPages> getSurveyPages(@Path("survey_id") String id);

    @Headers({Constants.SurveyMonkeyApi.AUTH_KEY})
    @GET("surveys/{survey_id}/pages/{page_id}/questions")
    Single<SearchResultQuestions> getPageQuestions(@Path("survey_id") String id, @Path("page_id") String pageId);

    @Headers({Constants.SurveyMonkeyApi.AUTH_KEY})
    @GET("surveys/{survey_id}/pages/{page_id}/questions/{question_id}")
    Single<SearchResultQuestionDetails> getQuestionDetails(@Path("survey_id") String surveyId, @Path("page_id") String pageId, @Path("question_id") String questionId);


}
