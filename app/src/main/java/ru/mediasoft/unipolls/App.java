package ru.mediasoft.unipolls;

import android.app.Application;

import ru.mediasoft.unipolls.data.net.NetworkService;

import ru.mediasoft.unipolls.data.repositories.DBRepository;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {
    private static App INSTANCE;

    //----------cicerone----------------------------------
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        cicerone = Cicerone.create();
    }

    public static NavigatorHolder getNavigatorHolder() {
        return INSTANCE.cicerone.getNavigatorHolder();
    }

    public static Router getRouter() {
        return INSTANCE.cicerone.getRouter();
    }
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