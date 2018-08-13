package ru.mediasoft.unipolls.domain.interactor;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.domain.dataclass.analytics.PollRollUps;

public class LoadSurveyRollUpsInteractor {
    public void loadSurveyRollUps(String token, String id, SingleObserver<PollRollUps> sub){
        App.getNetworkService().smApi.getSurveyRollUps(token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }
}
