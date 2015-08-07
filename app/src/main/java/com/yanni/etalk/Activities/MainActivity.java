package com.yanni.etalk.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanni.etalk.Fragments.AfterLoginFooterFragment;
import com.yanni.etalk.Fragments.AfterLoginHeaderFragment;
import com.yanni.etalk.Fragments.BeforeLoginFooterFragment;
import com.yanni.etalk.Fragments.BeforeLoginHeaderFragment;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.EtalkSharedPreference;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, BeforeLoginFooterFragment.OnLoginFooterInteractionListener,
        AfterLoginHeaderFragment.OnSettingBtnClickListener {

    private Context context;
    private Boolean loginState;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private TextView myBook;
    private TextView orderClass;
    private TextView myRecord;
    private TextView myComment;
    private TextView eClass;
    private TextView myPackage;


    static final int LOGIN = 1;
    static final int REGISTER = 2;
    static final int PROFILE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(EtalkSharedPreference.getPrefIsFirstTime(context).isEmpty()){
            Intent intent = new Intent(context,IntrodActivity.class);
            startActivity(intent);
        }else{
            System.out.println(EtalkSharedPreference.getPrefToken(this));

            loginState = EtalkSharedPreference.getPrefLoginState(context);
            myBook = (TextView) findViewById(R.id.my_book);
            myBook.setOnClickListener(this);
            myComment = (TextView) findViewById(R.id.my_comment);
            myComment.setOnClickListener(this);
            myRecord = (TextView) findViewById(R.id.my_record);
            myRecord.setOnClickListener(this);

            myPackage = (TextView) findViewById(R.id.my_package);
            myPackage.setOnClickListener(this);

            orderClass = (TextView) findViewById(R.id.order_class);
            orderClass.setOnClickListener(this);

            eClass = (TextView) findViewById(R.id.e_class);
            eClass.setOnClickListener(this);

            replaceHeader();
            replaceFooter();
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case LOGIN:
                if (resultCode == RESULT_OK) {
                    loginState = true;
                    replaceHeader();
                    replaceFooter();
                }
                break;
            case REGISTER:
                if (resultCode == RESULT_OK) {
                    loginState = true;
                    replaceHeader();
                    replaceFooter();
                }
                break;
            case PROFILE:
                if (resultCode == RESULT_OK) {
                    loginState = false;
                    EtalkSharedPreference.clearPreference(context);
                    replaceHeader();
                    replaceFooter();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void replaceHeader() {
        if (loginState) {
            ImageView midLogo = (ImageView) findViewById(R.id.mid_logo);
            midLogo.setBackgroundResource(R.mipmap.ic_logo_color);
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            AfterLoginHeaderFragment afterLoginHeaderFragment
                    = AfterLoginHeaderFragment.newInstance(EtalkSharedPreference.getPrefUserName(context),
                    EtalkSharedPreference.getPrefUserId(context),
                    EtalkSharedPreference.getPrefUserLevel(context),
                    EtalkSharedPreference.getPrefUserScore(context));
            fragmentTransaction.replace(R.id.header, afterLoginHeaderFragment);
            fragmentTransaction.commit();
        } else {
            ImageView midLogo = (ImageView) findViewById(R.id.mid_logo);
            midLogo.setBackgroundResource(R.mipmap.ic_logo_gray);
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.header, new BeforeLoginHeaderFragment());
            fragmentTransaction.commit();
        }
    }

    private void replaceFooter() {
        if (loginState) {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.footer, new AfterLoginFooterFragment());
            fragmentTransaction.commit();
        } else {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.footer, new BeforeLoginFooterFragment());
            fragmentTransaction.commit();
        }
    }

    //实现footer fragment中的接口，为注册和登陆按钮添加点击响应事件
    @Override
    public void onLoginFooterInteraction(int btnTag) {
        switch (btnTag) {
            case LOGIN:
                Intent loginIntent = new Intent(context, LoginActivity.class);
                startActivityForResult(loginIntent, LOGIN);
                break;
            case REGISTER:
                Intent registerIntent = new Intent(context, RegisterActivity.class);
                startActivityForResult(registerIntent, REGISTER);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSettingBtnClick() {
        Intent profileIntent = new Intent(context, UserProfileActivity.class);
        startActivityForResult(profileIntent, PROFILE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (loginState) {
            switch (view.getId()) {
                case R.id.my_book:
                    Intent myBookIntent = new Intent(context, MyBookActivity.class);
                    startActivity(myBookIntent);
                    break;
                case R.id.my_comment:
                    Intent myCommentIntent = new Intent(context, MyCommentActivity.class);
                    startActivity(myCommentIntent);
                    break;
                case R.id.my_record:
                    Intent myRecordIntent = new Intent(context, MyRecordActivity.class);
                    startActivity(myRecordIntent);
                    break;
                case R.id.my_package:
                    Intent myPackageIntent = new Intent(context, MyPackageActivity.class);
                    startActivity(myPackageIntent);
                    break;
                case R.id.order_class:
                    Intent bookClassIntent = new Intent(context, BookClassActivity.class);
                    startActivity(bookClassIntent);
                    break;
                case R.id.e_class:
                    break;
                default:
                    break;
            }
        } else {
            Intent loginIntent = new Intent(context, LoginActivity.class);
            startActivityForResult(loginIntent, LOGIN);
        }






    }
}
