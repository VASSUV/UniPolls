package ru.mediasoft.unipolls.other.events;

import ru.mediasoft.unipolls.App;

public class ShowErrorMessage {
    public Throwable tr;
    public App appContext;
    public ShowErrorMessage(Throwable tr, App appContext) {
        this.tr = tr;
        this.appContext = appContext;
    }
}
