package com.yanni.etalk.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.yanni.etalk.Adapters.SimpleAdapter;
import com.yanni.etalk.Entities.ToCommentCourse;
import com.yanni.etalk.Fragments.PostCommentFragment;
import com.yanni.etalk.Fragments.ToCommentListFragment;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.EtalkFragmentManager;
import com.yanni.etalk.Utilities.VolleySingleton;

import java.util.ArrayList;


public class MyCommentActivity extends AppCompatActivity implements ToCommentListFragment.OnCommentInteractionListener,
        PostCommentFragment.OnPostCommentFragmentInteractionListener, SimpleAdapter.ShowToCommentCourseListener {

    private Context context;
    private RequestQueue requestQueue;
    private ToCommentListFragment toCommentListFragment;
    private PostCommentFragment postCommentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);

        context = this;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

        toCommentListFragment = ToCommentListFragment.newInstance();
        EtalkFragmentManager.replaceFragment(context, R.id.toCommentCourses, toCommentListFragment);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_comment, menu);
        return true;
    }




    @Override
    public void onButtonInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                backToMainScreen();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemInteraction(ToCommentCourse toCommentCourse) {

    }


    @Override
    public void showToCommentCourses(ArrayList<ToCommentCourse> toCommentCourseArrayList) {
        System.out.println("activty called");
        postCommentFragment = PostCommentFragment.newInstance(toCommentCourseArrayList);
        EtalkFragmentManager.replaceFragment(context, R.id.toCommentCourses, postCommentFragment);
    }

    @Override
    public void onToolbarInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                EtalkFragmentManager.moveFragment(context, R.id.toCommentCourses, postCommentFragment, toCommentListFragment);
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
}
