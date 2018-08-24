package ru.mediasoft.unipolls.data.net;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyModel;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyRequestModel;
import ru.mediasoft.unipolls.domain.dataclass.GetAccessTokenModel;
import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.CreateQuestionModelRequest;
import ru.mediasoft.unipolls.domain.dataclass.createquestion.CreateQuestionModelResponse;
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

    @Headers(Constants.SurveyMonkeyApi.CONTENT_TYPE)
    @POST("v3/surveys/{poll_id}/pages/{page_id}/questions")
    Single<CreateQuestionModelResponse> createQuestion(@Header("Authorization") String token, @Path("poll_id") String poll_id, @Path("page_id") String page_id, @Body CreateQuestionModelRequest request);

    @Headers(Constants.SurveyMonkeyApi.CONTENT_TYPE)
    @PUT("v3/surveys/{poll_id}/pages/{page_id}/questions/{question_id}")
    Single<CreateQuestionModelResponse> replaceQuestion(@Header("Authorization") String token, @Path("poll_id") String poll_id, @Path("page_id") String page_id, @Path("question_id") String question_id, @Body CreateQuestionModelRequest request);

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

	@GET("v3/surveys/{poll_id}/rollups")
    Single<PollRollUps> getSurveyRollUps(@Header("Authorization") String token, @Path("poll_id") String id);

    //---------------------------------------

    @GET("v3/surveys/{survey_id}/pages")
    Single<SearchResultPages> getSurveyPages(@Header("Authorization") String token, @Path("survey_id") String id);

    @GET("v3/surveys/{survey_id}/pages/{page_id}/questions")
    Single<SearchResultQuestions> getPageQuestions(@Header("Authorization") String token, @Path("survey_id") String id, @Path("page_id") String pageId);

    @GET("v3/surveys/{survey_id}/pages/{page_id}/questions/{question_id}")
    Single<SearchResultQuestionDetails> getQuestionDetails(@Header("Authorization") String token, @Path("survey_id") String surveyId, @Path("page_id") String pageId, @Path("question_id") String questionId);
}
