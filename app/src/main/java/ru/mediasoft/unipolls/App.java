package ru.mediasoft.unipolls;

import android.app.Application;

import ru.mediasoft.unipolls.data.net.NetworkService;
import ru.mediasoft.unipolls.data.repositories.DBRepository;
import ru.mediasoft.unipolls.data.repositories.SharedPrefRepository;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {

    public static App INSTANCE;

    private Cicerone<Router> cicerone;


    //----------------SharedPrefRep--------------------------------
    private SharedPrefRepository sharedPrefRepository;

    public static SharedPrefRepository getSharPref(){
        return INSTANCE.sharedPrefRepository;
    }
    //----------------SharedPrefRep--------------------------------

    //----------------cicerone-------------------------------------
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

    final private NetworkService networkService = new NetworkService();
    public static NetworkService getNetworkService() {
        return INSTANCE.networkService;
    }

    final private DBRepository dbRepository = new DBRepository(this);
    public static DBRepository getDBRepository() {
        return INSTANCE.dbRepository;
    }

}