package ru.mediasoft.unipolls.other;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.mediasoft.unipolls.presentation.login.LoginFragment;
import ru.mediasoft.unipolls.presentation.mysurveys.MySurveysFragment;
import ru.mediasoft.unipolls.presentation.registration.RegistrationFragment;
import ru.mediasoft.unipolls.presentation.userInfo.UserInfoFragment;

public enum Screen {
    SPLASH,
    START,
    USERINFO,
    REGISTRATION, MYSURVEYS;

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
            default:
                break;
        }
        return null;
    }
}
