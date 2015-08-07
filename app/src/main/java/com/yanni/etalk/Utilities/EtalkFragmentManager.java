package com.yanni.etalk.Utilities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

/**
 * Created by macbookretina on 29/06/15.
 */
public class EtalkFragmentManager {

    public static void moveFragment(Context context, int containerId, Fragment from, Fragment to) {
        android.app.FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        if (to.isAdded()) {
            fragmentTransaction.hide(from).show(to);
        } else {
            fragmentTransaction.hide(from).add(containerId, to);
        }
        fragmentTransaction.commit();
    }


    public static void addFragment(Context context, int containerId, Fragment fragment) {
        android.app.FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.add(containerId, fragment);
        fragmentTransaction.commit();
    }


    public static void replaceFragment(Context context, int containerId, Fragment fragment) {
        android.app.FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commit();
    }

}
