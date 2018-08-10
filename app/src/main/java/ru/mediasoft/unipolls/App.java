package ru.mediasoft.unipolls;

import android.app.Application;

import ru.mediasoft.unipolls.data.net.NetworkService;

import ru.mediasoft.unipolls.data.repositories.DBRepository;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {


    //----------------SharedPrefRep--------------------------------
    private SharedPrefRepository sharedPrefRepository;

    public static SharedPrefRepository getSharPref(){
        return INSTANCE.sharedPrefRepository;
    }
    //----------------SharedPrefRep--------------------------------

    //----------------cicerone-------------------------------------
    public static App INSTANCE;

    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPrefRepository = new SharedPrefRepository(getApplicationContext());
        INSTANCE = this;
        cicerone = Cicerone.create();
    }

    public static NavigatorHolder getNavigatorHolder() {
        return INSTANCE.cicerone.getNavigatorHolder();
    }

    public static Router getRouter() {
        return INSTANCE.cicerone.getRouter();
    }
    //---------------cicerone--------------------------------------

    //-----------------------------------------------------

    public static NetworkService getNetworkService() {
        return INSTANCE.networkService;
    }

    public static DBRepository getDBRepository() {
        return INSTANCE.dbRepository;
    }

    final private NetworkService networkService = new NetworkService();
    final private DBRepository dbRepository = new DBRepository(this);
}