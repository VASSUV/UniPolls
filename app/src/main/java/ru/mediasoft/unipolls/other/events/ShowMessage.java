package ru.mediasoft.unipolls.other.events;

import android.content.Context;

public class ShowMessage {
    public String message;
    public Context ctx;
    public ShowMessage(String message, Context ctx){
        this.message = message;
        this.ctx = ctx;
    }
}
