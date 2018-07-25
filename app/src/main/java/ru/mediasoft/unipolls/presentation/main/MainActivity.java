package ru.mediasoft.unipolls.presentation.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.presentation.Screen;
import ru.mediasoft.unipolls.presentation.testfrag.TestFragment;
import ru.mediasoft.unipolls.presentation.userInfo.UserInfoFragment;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static ru.mediasoft.unipolls.presentation.Screen.TEST;

public class MainActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, new UserInfoFragment())
//                .addToBackStack(null)
//                .commit();
        App.getRouter().newRootScreen(Screen.START.name());

    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(), R.id.fragment_container){

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey){
                case Screen.START:
                    return new UserInfoFragment();
            }
        }

        @Override
        protected void showSystemMessage(String message) {

        }

        @Override
        protected void exit() {

        }
    };
}
