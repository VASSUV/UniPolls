package ru.mediasoft.unipolls.presentation.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.R;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.other.Screen;
import ru.mediasoft.unipolls.other.router.CustomNavigator;

public class MainActivity extends MvpAppCompatActivity implements MainView{

    @InjectPresenter
    MainPresenter presenter;

    CustomNavigator.OnChangeFragmentListener listener;
    private Screen screen;

    private View loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loader = findViewById(R.id.pre_loader);

        App.getNavigatorHolder().setNavigator(navigator);
        App.getRouter().newRootScreen(Screen.START.name());
        listener = () -> {
            switch (screen) {

            }
        };
        Log.i(Constants.LOG_TAG_DB, "info: ");
        App.getDBRepository().getPagesToLogs();
        App.getDBRepository().getPollsToLogs();
        App.getDBRepository().getQuestionsToLogs();
    }

    private CustomNavigator navigator = new CustomNavigator(getSupportFragmentManager(), R.id.fragment_container, listener) {

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            screen = Screen.valueOf(screenKey);
            if (screen == null) {
                showSystemMessage("Unknown screenKey!", 1);
                return null;
            }

            return screen.create((Bundle) data);
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
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getNavigatorHolder().removeNavigator();
    }

    public void setActionBarTitle(String title) {
        setTitle(title);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }
    @Override
    public void showMessage(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }
}
