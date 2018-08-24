package ru.mediasoft.unipolls.other;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.mediasoft.unipolls.presentation.addpoll.AddingPollFragment;
import ru.mediasoft.unipolls.presentation.addquest.AddQuestFragment;
import ru.mediasoft.unipolls.presentation.analytics.AnalyticsFragment;
import ru.mediasoft.unipolls.presentation.detail.DetailPollFragment;
import ru.mediasoft.unipolls.presentation.editpoll.EditPollFragment;
import ru.mediasoft.unipolls.presentation.editquest.EditQuestFragment;
import ru.mediasoft.unipolls.presentation.login.LoginFragment;
import ru.mediasoft.unipolls.presentation.polls.PollListFragment;
import ru.mediasoft.unipolls.presentation.questions.QuestionsFragment;
import ru.mediasoft.unipolls.presentation.registration.RegistrationFragment;
import ru.mediasoft.unipolls.presentation.splash.SplashFragment;
import ru.mediasoft.unipolls.presentation.userInfo.UserInfoFragment;

public enum Screen {
    SPLASH,
    START,
    USERINFO,
    REGISTRATION,
    POLL_LIST,
    ADDING_POLL,
    QUESTIONS,
    DETAIL, ANALYTICS, EDITPOLL, EDITQUEST, ADDQUEST;


    public MvpAppCompatFragment create(Bundle args) {
        switch (this) {
            case START:
                return LoginFragment.newInstance();
            case USERINFO:
                return UserInfoFragment.newInstance();
            case REGISTRATION:
                return RegistrationFragment.newInstance();
            case SPLASH:
                return SplashFragment.newInstance();
            case POLL_LIST:
                return PollListFragment.newInstance();
            case ADDING_POLL:
                return AddingPollFragment.newInstance();
            case DETAIL:
                return DetailPollFragment.newInstance(args);
            case QUESTIONS:
                return QuestionsFragment.newInstance(args);
            case ANALYTICS :
                return AnalyticsFragment.newInstance(args);
            case EDITPOLL:
                return EditPollFragment.newInstance(args);
            case EDITQUEST:
                return EditQuestFragment.newInstance(args);
            case ADDQUEST:
                return AddQuestFragment.newInstance(args);
            default:
                return null;
        }
    }


    public MvpAppCompatFragment create() {
        return create(new Bundle());
    }
}
