package ru.mediasoft.unipolls.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;
import ru.mediasoft.unipolls.dataclass.UserInfoModel;

import static ru.mediasoft.unipolls.Constants.AUTH_BEARER;

public interface UserInfoApi {

    @Headers(AUTH_BEARER)
    @GET
    Single<UserInfoModel> getUserInfo(@Url String url);

    String BASE_URL = "https://api.surveymonkey.com/v3/";
}
