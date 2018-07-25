package ru.mediasoft.unipolls.presentation.testfrag;

import ru.mediasoft.unipolls.App;
import ru.terrakok.cicerone.Router;

public class TestPresenter {
    private Router router;
    TestView testView;

    public void onCreate(App applicationContext, TestView testView) {
        this.testView = testView;
    }
}
