package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.NewSurveyTitleModel;

public class PatchSurveyNameInteractor {
    public void patchSurvey(String token, String pollId, String newName, SingleObserver<Object> sub){
        App.getNetworkService().smApi.changeSuveyName(token, pollId, new NewSurveyTitleModel(newName))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(sub);
    }
}
