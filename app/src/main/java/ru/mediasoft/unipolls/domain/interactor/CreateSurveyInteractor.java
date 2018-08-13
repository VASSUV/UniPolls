package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.data.net.SMApi;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyModel;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyRequestModel;

public class CreateSurveyInteractor {

    private SMApi smApi;

    public CreateSurveyInteractor(SMApi smApi){
        this.smApi = smApi;
    }

    public void createSurvey(String token, String name, SingleObserver<CreateSurveyModel> sub){
        App.getNetworkService().smApi.createSurvey(token, new CreateSurveyRequestModel(name))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
