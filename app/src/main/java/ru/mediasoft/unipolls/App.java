package ru.mediasoft.unipolls;

import com.arellomobile.mvp.MvpApplication;

import ru.mediasoft.unipolls.data.NetworkService;
import java.util.ArrayList;
import java.util.List;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends MvpApplication {

    //----------------cicerone-------------------------------------
    public static App INSTANCE;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public static Router getRouter() {
        return INSTANCE.cicerone.getRouter();
    }

    //----------------cicerone-------------------------------------

    //----------------webview data---------------------------------
    public static String USER_CODE;
    //----------------webview data---------------------------------


    final public NetworkService networkService = new NetworkService();
}