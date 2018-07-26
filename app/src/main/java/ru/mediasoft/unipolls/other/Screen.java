package ru.mediasoft.unipolls.other;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.mediasoft.unipolls.presentation.login.LoginFragment;
import ru.mediasoft.unipolls.presentation.userInfo.UserInfoFragment;

public enum Screen {
    SPLASH,
    TEST,
    START, USERINFO;

    public MvpAppCompatFragment create() {
        switch (this) {
            case START:
                return LoginFragment.newInstance();
            case USERINFO:
                return UserInfoFragment.newInstance();
//            case TEST:
//                return TestFragment.newInstance();
//            case SPLASH:
//                return SplashFragment.newInstance();
        }
        return null;
    }
}
