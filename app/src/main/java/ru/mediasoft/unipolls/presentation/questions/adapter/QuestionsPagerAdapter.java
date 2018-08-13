package ru.mediasoft.unipolls.presentation.questions.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.mediasoft.unipolls.App;
import ru.mediasoft.unipolls.other.Constants;
import ru.mediasoft.unipolls.presentation.currentquestion.CurrentQuestionFragment;

public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    private Bundle args;

    public QuestionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setArgs(Bundle args) {
        this.args = args;
    }

    @Override
    public Fragment getItem(int position) {
        int pos = position + 1;
        args.putInt(Constants.BundleKeys.QUESTION_POSITION, pos);

        return CurrentQuestionFragment.newInstance(args);
    }

    @Override
    public int getCount() {
        return args == null ? 0 : args.getInt(Constants.BundleKeys.PAGE_QUESTIONS_COUNT);
    }


    /*public void onSelectedQuestion(int position){
       new Handler().postDelayed(()->((CurrentQuestionFragment)getItem(position)).onSelectedQuestion(), 500);
    }*/
}
