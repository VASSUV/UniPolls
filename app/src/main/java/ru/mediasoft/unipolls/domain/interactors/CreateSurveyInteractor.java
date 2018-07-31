package ru.mediasoft.unipolls.domain.interactors;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.data.SMApi;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyModel;
import ru.mediasoft.unipolls.domain.dataclass.CreateSurveyRequestModel;

public class CreateSurveyInteractor {

    private SMApi smApi;

    public CreateSurveyInteractor(SMApi smApi){
        this.smApi = smApi;
    }

    public void createSurvey(String name, SingleObserver<CreateSurveyModel> sub){
        App.INSTANCE.networkService.smApi.createSurvey(new CreateSurveyRequestModel(name))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
