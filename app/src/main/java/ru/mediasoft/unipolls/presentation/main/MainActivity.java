package ru.mediasoft.unipolls.presentation.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.router.CustomNavigator;

public class MainActivity extends MvpAppCompatActivity {

    CustomNavigator.OnChangeFragmentListener listener;
    private Screen screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
        App.getRouter().newRootScreen("START");
        listener = () -> {
            switch (screen){
            }
        };
    }

    private CustomNavigator navigator = new CustomNavigator(getSupportFragmentManager(), R.id.fragment_container, listener) {

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
           screen = Screen.valueOf(screenKey);
            if(screen == null) {
                showSystemMessage("Unknown screenKey!", 1);
                return null;
            }
           return screen.create();
        }

        @Override
        protected void showSystemMessage(String message, int type) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void exit() {
            finish();
        }

        @Override
        protected int getEnterAnimation(String oldScreenKey, String newScreenKey) {
            return 0;
        }

        @Override
        protected int getExitAnimation(String oldScreenKey, String newScreenKey) {
            return 0;
        }

        @Override
        protected int getPopEnterAnimation(String oldScreenKey, String newScreenKey) {
            return 0;
        }

        @Override
        protected int getPopExitAnimation(String oldScreenKey, String newScreenKey) {
            return 0;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.INSTANCE.getNavigatorHolder().removeNavigator();
    }
}
