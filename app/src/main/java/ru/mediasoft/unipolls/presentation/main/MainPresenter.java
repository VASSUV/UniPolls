package ru.mediasoft.unipolls.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.events.HideLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowLoaderEvent;
import ru.mediasoft.unipolls.other.events.ShowMessage;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public void onStart(){
        EventBus.getDefault().register(this);
    }

    public void onStop(){
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onShowLoaderEvent(ShowLoaderEvent event){
        getViewState().showLoader();
    }

    @Subscribe
    public void onHideLoaderEvent(HideLoaderEvent event){
        getViewState().hideLoader();
    }

    @Subscribe
    public void onShowMessage(ShowMessage event){
        getViewState().showMessage(event.message);
    }

    public void setRootScreen() {
        if(App.getSharPref().getToken().isEmpty()){
            App.getRouter().newRootScreen(Screen.START.name());
        }
        else{
            App.getRouter().newRootScreen(Screen.USERINFO.name());
        }
    }

    public void clearPref() {
        App.getSharPref().removeCodeAndToken();
    }
}
