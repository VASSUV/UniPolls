package ru.mediasoft.unipolls.other;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.mediasoft.unipolls.presentation.splash.SplashFragment;
import ru.mediasoft.unipolls.presentation.testfrag.TestFragment;
import ru.mediasoft.unipolls.presentation.userInfo.UserInfoFragment;

public enum Screen {
    SPLASH,
    TEST,
    START;

    public static MvpAppCompatFragment create(Screen key) {
        switch (key) {
            case START:
                return UserInfoFragment.newInstance();
            case TEST:
                return TestFragment.newInstance();
            case SPLASH:
                return SplashFragment.newInstance();
        }
        return null;
    }
}
