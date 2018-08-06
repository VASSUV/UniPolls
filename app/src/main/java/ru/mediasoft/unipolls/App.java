package ru.mediasoft.unipolls;

import android.app.Application;

import ru.mediasoft.unipolls.data.NetworkService;
import ru.mediasoft.unipolls.data.repositories.SharedPrefRepository;
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

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public static Router getRouter() {
        return INSTANCE.cicerone.getRouter();
    }
    //---------------cicerone--------------------------------------

    final public NetworkService networkService = new NetworkService();

}