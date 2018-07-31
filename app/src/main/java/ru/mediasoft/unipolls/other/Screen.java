package ru.mediasoft.unipolls.other;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.mediasoft.unipolls.presentation.login.LoginFragment;
import ru.mediasoft.unipolls.presentation.mysurveys.MySurveysFragment;
import ru.mediasoft.unipolls.presentation.newsurveyname.NewSurveyNameFragment;
import ru.mediasoft.unipolls.presentation.registration.RegistrationFragment;
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
    DETAIL;


    public MvpAppCompatFragment create() {
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
            default:
                break;
            case SPLASH:
                return SplashFragment.newInstance();
            case POLL_LIST:
                return PollListFragment.newInstance();
        }
        return null;
    }
}
