package ru.mediasoft.unipolls.data;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyModel;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyRequestModel;
import ru.mediasoft.unipolls.domain.dataclass.UserInfoModel;

import static ru.mediasoft.unipolls.other.Constants.AUTH_BEARER;
import static ru.mediasoft.unipolls.other.Constants.CONTENTTYPE;

public interface SMApi {

    @Headers({AUTH_BEARER, CONTENTTYPE})
    @GET("users/me")
    Single<UserInfoModel> getUserInfo();

    @Headers({AUTH_BEARER, CONTENTTYPE})
    @POST("surveys")
    Single<CreateSurveyModel> createSurvey(@Body CreateSurveyRequestModel request);

    String BASE_URL = "https://api.surveymonkey.com/v3/";
}
