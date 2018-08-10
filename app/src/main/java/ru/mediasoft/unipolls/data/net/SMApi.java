package ru.mediasoft.unipolls.data.net;

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
import ru.mediasoft.unipolls.domain.dataclass.pollpages.SearchResultPages;
import ru.mediasoft.unipolls.domain.dataclass.pollquestiondetail.SearchResultQuestionDetails;
import ru.mediasoft.unipolls.domain.dataclass.pollquestions.SearchResultQuestions;
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
    @GET("surveys/{id}")
    Single<SearchResultDetails> getSurveyDetails(@Path("id") String id);

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
