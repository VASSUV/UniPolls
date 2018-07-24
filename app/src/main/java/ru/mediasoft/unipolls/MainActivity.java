package ru.mediasoft.unipolls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.mediasoft.unipolls.UserInfoApi;
import ru.mediasoft.unipolls.App;

public class MainActivity extends AppCompatActivity {

    TextView first_name;
    TextView sec_name;
    TextView email;
    Button get;
    UserInfoApi userInfoApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first_name = findViewById(R.id.first_name);
        sec_name = findViewById(R.id.second_name);
        email = findViewById(R.id.email);

        get = findViewById(R.id.getInfo);
        get.setOnClickListener(this::onGetButtoClick);
        userInfoApi = ((App) getApplicationContext()).networkService.userInfoApi;
    }

    public void onGetButtoClick(View view) {
        Disposable disposable = userInfoApi.getUserInfo("https://api.surveymonkey.com/v3/users/me")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(userInfoModel -> {
                    first_name.setText(userInfoModel.first_name}),
                        throwable -> showErrorMessage(throwable.getMessage()));
    }

    private void showErrorMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
