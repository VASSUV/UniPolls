package ru.mediasoft.unipolls.presentation.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.Screen;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class MainActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
        App.getRouter().newRootScreen("START");
    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(), R.id.fragment_container) {

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey) {
                case "START":
                    return Screen.create(Screen.START);
                case "SPLASH":
                    return Screen.create(Screen.SPLASH);
                case "TEST":
                    return Screen.create(Screen.TEST);
                default:
                    showSystemMessage("Unknown screenkey!");
            }
            return null;
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void exit() {
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.INSTANCE.getNavigatorHolder().removeNavigator();
    }
}
