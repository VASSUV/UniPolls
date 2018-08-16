package ru.mediasoft.unipolls.presentation.editpoll;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.other.Screen;

@InjectViewState
public class EditPollPresenter extends MvpPresenter<EditPollView> {

    public void goToEditQuest(Bundle args) {
        App.getRouter().navigateTo(Screen.EDITQUEST.name(), args);
    }

    public void goToAddQuest(Bundle args) {
        App.getRouter().navigateTo(Screen.ADDQUEST.name());
    }
}
