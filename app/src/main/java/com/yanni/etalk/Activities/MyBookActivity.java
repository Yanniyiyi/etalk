package com.yanni.etalk.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yanni.etalk.Adapters.BookCourseListAdapter;
import com.yanni.etalk.Entities.BookedCourse;
import com.yanni.etalk.Fragments.BookedCourseDetailFragment;
import com.yanni.etalk.Fragments.MyBookFragment;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.EtalkFragmentManager;


public class MyBookActivity extends AppCompatActivity implements BookCourseListAdapter.ShowBookedCourseDetailListener,
        MyBookFragment.OnMyBookFragmentInteractionListener,
        BookedCourseDetailFragment.OnBookedCourseDetailInteractionListener {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book);
        context = this;
        EtalkFragmentManager.addFragment(context, R.id.my_book_body, new MyBookFragment());
    }


    @Override
    public void ShowBookedCourseDetail(BookedCourse bookedCourse) {
        EtalkFragmentManager.replaceFragment(context, R.id.my_book_body, BookedCourseDetailFragment.newInstance(bookedCourse));
    }

    @Override
    public void onMyBookFragmentInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                System.out.println("invoked");
                backToMainActivity();
                break;
            case R.id.go_to_book:
                bookClass();
                break;
            default:
                break;
        }
    }

    private void backToMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBookedCourseDetailInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                EtalkFragmentManager.replaceFragment(context, R.id.my_book_body, new MyBookFragment());
                break;
            case R.id.backToMainScreen:
                backToMainActivity();
                break;

            default:
                break;
        }
    }

    @Override
    public void onCourseCanceledInteraction() {
        EtalkFragmentManager.replaceFragment(context, R.id.my_book_body, new MyBookFragment());
    }

    private void bookClass(){
        Intent intent = new Intent(context,BookClassActivity.class);
        startActivity(intent);
    }
}
