package ru.mediasoft.unipolls.other;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.mediasoft.unipolls.presentation.questions.QuestionsFragment;
import ru.mediasoft.unipolls.presentation.login.LoginFragment;
import ru.mediasoft.unipolls.presentation.mysurveys.MySurveysFragment;
import ru.mediasoft.unipolls.presentation.newsurveyname.NewSurveyNameFragment;
import ru.mediasoft.unipolls.presentation.registration.RegistrationFragment;
import ru.mediasoft.unipolls.presentation.addpoll.AddingPollFragment;
import ru.mediasoft.unipolls.presentation.detail.DetailPollFragment;
import ru.mediasoft.unipolls.presentation.polls.PollListFragment;
import ru.mediasoft.unipolls.presentation.splash.SplashFragment;
import ru.mediasoft.unipolls.presentation.userInfo.UserInfoFragment;

public enum Screen {
    SPLASH,
    START,
    USERINFO,
    REGISTRATION,
    MYSURVEYS,
    NEWSURVEYNAME,
    POLL_LIST,
    ADDING_POLL,
    QUESTIONS,
    DETAIL;


    public MvpAppCompatFragment create(Bundle args) {
        switch (this) {
            case START:
                return LoginFragment.newInstance();
            case USERINFO:
                return UserInfoFragment.newInstance();
            case REGISTRATION:
                return RegistrationFragment.newInstance();
            case MYSURVEYS:
                return MySurveysFragment.newInstance();
            case NEWSURVEYNAME:
                return NewSurveyNameFragment.newInstance();
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
            default:
                return null;
        }
    }


    public MvpAppCompatFragment create() {
        return create(new Bundle());
    }
}
