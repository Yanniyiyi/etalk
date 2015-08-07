package com.yanni.etalk.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.yanni.etalk.Fragments.MyPackageFragment;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.EtalkFragmentManager;


public class MyPackageActivity extends AppCompatActivity implements MyPackageFragment.OnMyPackageInteractionListener {

    private Context context;
    private MyPackageFragment myPackageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_package);

        context = this;
        myPackageFragment = new MyPackageFragment();


        EtalkFragmentManager.addFragment(context, R.id.my_package_body, myPackageFragment);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_package, menu);
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


    @Override
    public void onMyPackageInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                backToMainActivity();
                break;
            default:
                break;
        }
    }

    private void backToMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
