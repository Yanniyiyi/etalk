package com.yanni.etalk.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.yanni.etalk.Adapters.DailyRecordListAdapter;
import com.yanni.etalk.Entities.DailyRecord;
import com.yanni.etalk.Entities.MonthlyRecord;
import com.yanni.etalk.Fragments.DailyRecordFragment;
import com.yanni.etalk.Fragments.MonthlyRecordFragment;
import com.yanni.etalk.Fragments.ShowTeacherStudentCommentFragment;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.EtalkFragmentManager;

public class MyRecordActivity extends ActionBarActivity implements MonthlyRecordFragment.OnFragmentInteractionListener,
        DailyRecordFragment.OnFragmentInteractionListener, DailyRecordListAdapter.ShowCommentListener,
        ShowTeacherStudentCommentFragment.OnShowCommentFragmentInteractionListener {

    private Context context;
    private MonthlyRecordFragment monthlyRecordFragment;
    private DailyRecordFragment dailyRecordFragment;
    private MonthlyRecord monthlyRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
        context = this;
        monthlyRecordFragment = new MonthlyRecordFragment();
        EtalkFragmentManager.addFragment(context, R.id.month_record_body, monthlyRecordFragment);
    }


    @Override
    public void onButtonInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                backToMainActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemInteraction(MonthlyRecord monthlyRecord) {
        System.out.println("called");
        this.monthlyRecord = monthlyRecord;
        dailyRecordFragment = DailyRecordFragment.newInstance(monthlyRecord);
        EtalkFragmentManager.replaceFragment(context, R.id.month_record_body, dailyRecordFragment);
    }


    @Override
    public void onFragmentInteraction() {
        EtalkFragmentManager.replaceFragment(context, R.id.month_record_body, new MonthlyRecordFragment());
    }

    @Override
    public void onDailyRecordToolbarClick(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                System.out.println("hello clicked");
                EtalkFragmentManager.replaceFragment(context, R.id.month_record_body, new MonthlyRecordFragment());
                break;
            case R.id.backToMainScreen:
                backToMainActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void showTeacherComment(DailyRecord dailyRecord) {
        System.out.println("show teacher comment called");
        EtalkFragmentManager.replaceFragment(context, R.id.month_record_body, ShowTeacherStudentCommentFragment.newInstance(dailyRecord, 0));
    }

    @Override
    public void showStudentComment(DailyRecord dailyRecord) {
        System.out.println("show student comment called");
        EtalkFragmentManager.replaceFragment(context, R.id.month_record_body, ShowTeacherStudentCommentFragment.newInstance(dailyRecord, 1));
    }

    private void backToMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onShowCommentToolbarClick(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                EtalkFragmentManager.replaceFragment(context, R.id.month_record_body, DailyRecordFragment.newInstance(monthlyRecord));
                break;
            case R.id.backToMainScreen:
                backToMainActivity();
                break;
        }
    }
}
