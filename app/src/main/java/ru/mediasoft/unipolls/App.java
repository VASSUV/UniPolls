package ru.mediasoft.unipolls;

import android.app.Application;

public class App extends Application {

    final public NetworkService networkService = new NetworkService();
}