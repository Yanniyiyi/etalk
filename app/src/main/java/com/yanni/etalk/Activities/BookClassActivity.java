package com.yanni.etalk.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yanni.etalk.Adapters.BookClassListAdapter;
import com.yanni.etalk.Entities.EtalkPackage;
import com.yanni.etalk.Fragments.BookClassDetailFragment;
import com.yanni.etalk.Fragments.BookClassListFragment;
import com.yanni.etalk.Fragments.SelectBookDateFragment;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.DateUtility;
import com.yanni.etalk.Utilities.EtalkFragmentManager;

public class BookClassActivity extends AppCompatActivity implements BookClassListAdapter.ShowBookClassDetailListener,
        BookClassDetailFragment.OnBookClassDetailInteractionListener,
        BookClassListFragment.OnBookClassListInteractionListener,
        SelectBookDateFragment.OnSelectBookDateInteractionListener {

    private Context context;
    private EtalkPackage etalkPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_book_class);

        EtalkFragmentManager.addFragment(context, R.id.book_class_body, new BookClassListFragment());

    }

    @Override
    public void showBookClassDetail(EtalkPackage etalkPackage) {
        this.etalkPackage = etalkPackage;
        EtalkFragmentManager.replaceFragment(context, R.id.book_class_body, BookClassDetailFragment.newInstance(this.etalkPackage, DateUtility.getCurrentDate()));
    }


    @Override
    public void onBookClassListInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                backToMainScreen();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBookClassDetailInteraction(int viewId, String date) {
        switch (viewId) {
            case R.id.current_date:
                EtalkFragmentManager.replaceFragment(context, R.id.book_class_body, SelectBookDateFragment.newInstance(date));
                break;
            case R.id.backBtn:
                EtalkFragmentManager.replaceFragment(context, R.id.book_class_body, new BookClassListFragment());
                break;
            case R.id.backToMainScreen:
                backToMainScreen();
                break;
        }
    }

    @Override
    public void onSelectBookDateInteraction(int viewId, String strDate) {
        switch (viewId) {
            case R.id.backBtn:
                EtalkFragmentManager.replaceFragment(context, R.id.book_class_body, BookClassDetailFragment.newInstance(this.etalkPackage, strDate));
                break;
            case R.id.backToMainScreen:
                backToMainScreen();
                break;
            default:
                break;
        }
    }

    private void backToMainScreen() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDateChanged(String strDate) {
        EtalkFragmentManager.replaceFragment(context, R.id.book_class_body, BookClassDetailFragment.newInstance(this.etalkPackage, strDate));
    }
}
