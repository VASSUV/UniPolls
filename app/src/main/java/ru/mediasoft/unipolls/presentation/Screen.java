package ru.mediasoft.unipolls.presentation;

import android.support.v4.app.Fragment;

import ru.mediasoft.unipolls.presentation.splash.SplashFragment;
import ru.mediasoft.unipolls.presentation.testfrag.TestFragment;
import ru.mediasoft.unipolls.presentation.userInfo.UserInfoFragment;

public enum Screen {
    SPLASH,
    TEST,
    START;


    Fragment create(){
        switch (this){
            case TEST:
                return new TestFragment();
            case START:
                return new UserInfoFragment();
        }
    }
}
