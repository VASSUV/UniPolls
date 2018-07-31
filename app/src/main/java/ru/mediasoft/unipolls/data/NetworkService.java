package ru.mediasoft.unipolls.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ru.mediasoft.unipolls.other.Constants;

public class NetworkService {

    final private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.SurveyMonkeyApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    final public SMApi smApi = retrofit.create(SMApi.class);
}
