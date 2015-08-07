package com.yanni.etalk.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Fragments.ChangePasswordFragment;
import com.yanni.etalk.Fragments.ChangePhoneNumFragment;
import com.yanni.etalk.Fragments.ChangeQQFragment;
import com.yanni.etalk.Fragments.UserProfileFragment;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.ErrorHelper;
import com.yanni.etalk.Utilities.EtalkFragmentManager;
import com.yanni.etalk.Utilities.EtalkSharedPreference;
import com.yanni.etalk.Utilities.JsonObjectGenerator;
import com.yanni.etalk.Utilities.UrlManager;
import com.yanni.etalk.Utilities.ValidateUtility;
import com.yanni.etalk.Utilities.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


public class UserProfileActivity extends ActionBarActivity
        implements UserProfileFragment.OnFragmentInteractionListener,
        ChangePasswordFragment.OnChangePasswordInteractionListener,
        ChangePhoneNumFragment.OnChangePhoneNumInteractionListener,
        ChangeQQFragment.OnChangeQQInteractionListener {

    private Context context;
    private ChangePasswordFragment changePasswordFragment;
    private UserProfileFragment userProfileFragment;
    private ChangePhoneNumFragment changePhoneNumFragment;
    private ChangeQQFragment changeQQFragment;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        context = this;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        userProfileFragment = new UserProfileFragment();
        changePasswordFragment = new ChangePasswordFragment();
        changePhoneNumFragment = new ChangePhoneNumFragment();
        changeQQFragment = new ChangeQQFragment();

        EtalkFragmentManager.addFragment(context, R.id.profilePage, userProfileFragment);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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


    private void logout() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);

        Button confirm = (Button) dialog.findViewById(R.id.confirm_logout);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_logout);
        dialog.show();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.DKGRAY).build();

                progressDialog.show();
                JSONObject obj = JsonObjectGenerator.createLogOutJsonObject(EtalkSharedPreference.getPrefUserId(context));
          final RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlManager.LOGOUT_URL, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                           // System.out.println(response.toString());

                            if (response.getString("status").equals("0")) {
                                // progressbar gone
                                System.out.println(response.getString("error_code"));
                                ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                            } else {

                                Intent returnIntent = getIntent();
                                setResult(RESULT_OK, returnIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", EtalkSharedPreference.getPrefToken(context));
                        headers.put("Content-type", "application/json;charset=UTF-8");
                        return headers;
                    }
                };

                requestQueue.add(jsonObjectRequest);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        //progressbar


    }

    private void saveNewPassword() {
        String originalPassword = ((TextView) findViewById(R.id.originalPassword))
                .getText().toString();
        String newPassword = ((TextView) findViewById(R.id.newPassword))
                .getText().toString();
        String confirmNewPassword = ((TextView) findViewById(R.id.confirmNewPassword))
                .getText().toString();
        if (originalPassword.isEmpty()) {
            Toast.makeText(context, "请输入原密码", Toast.LENGTH_SHORT).show();
        } else if (newPassword.isEmpty()) {
            Toast.makeText(context, "请输入新密码", Toast.LENGTH_SHORT).show();
        } else if (confirmNewPassword.isEmpty()) {
            Toast.makeText(context, "请输确认密码", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(context, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();

            progressDialog.show();
            JSONObject obj = JsonObjectGenerator.createChangePasswordJsonObject(
                    EtalkSharedPreference.getPrefUserId(context), originalPassword, newPassword);
//            final RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
            JsonObjectRequest changePasswordRequest = new JsonObjectRequest(Request.Method.PUT,
                    UrlManager.CHANGE_PASSWORD_URL, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        System.out.println(response.toString());

                        if (response.getString("status").equals("0")) {
                            // progressbar gone
                            ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                        } else {
                            EtalkSharedPreference.setPrefToken(context, response.getString("token"));
                            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            EtalkFragmentManager.moveFragment(context, R.id.profilePage,
                                    changePasswordFragment, userProfileFragment);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", EtalkSharedPreference.getPrefToken(context));
                    headers.put("Content-type", "application/json;charset=UTF-8");
                    return headers;
                }
            };
            requestQueue.add(changePasswordRequest);
        }
    }


    private void saveNewPhoneNum() {

        String originalPhoneNum = ((TextView) findViewById(R.id.originalPhoneNum))
                .getText().toString();
        String newPhoneNum = ((TextView) findViewById(R.id.newPhoneNum))
                .getText().toString();

        if (!ValidateUtility.PhoneNum(originalPhoneNum)) {
            Toast.makeText(context, "原手机号码无效", Toast.LENGTH_SHORT).show();
        } else if (!ValidateUtility.PhoneNum(newPhoneNum)) {
            Toast.makeText(context, "新手机号码无效", Toast.LENGTH_SHORT).show();
        } else {
            final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();
            progressDialog.show();
            JSONObject obj = JsonObjectGenerator.createChangePhoneNumJsonObject(
                    EtalkSharedPreference.getPrefUserId(context), originalPhoneNum, newPhoneNum);
            JsonObjectRequest changePasswordRequest = new JsonObjectRequest(Request.Method.PUT,
                    UrlManager.CHANGE_PHONE_NUM_URL, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        System.out.println(response.toString());

                        if (response.getString("status").equals("0")) {
                            // progressbar gone
                            ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                        } else {
                            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();

                            EtalkFragmentManager.moveFragment(context, R.id.profilePage,
                                    changePhoneNumFragment, userProfileFragment);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", EtalkSharedPreference.getPrefToken(context));
                    headers.put("Content-type", "application/json;charset=UTF-8");
                    return headers;
                }
            };
            requestQueue.add(changePasswordRequest);
        }
    }


    private void saveNewQQNum() {
        String newQQNum = ((TextView) findViewById(R.id.newQQNum))
                .getText().toString();
        if (!ValidateUtility.QQNum(newQQNum)) {
            Toast.makeText(context, "QQ号码无效", Toast.LENGTH_SHORT).show();
        } else {
            final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();
            progressDialog.show();
            JSONObject obj = JsonObjectGenerator.createChangeQQNumJsonObject(
                    EtalkSharedPreference.getPrefUserId(context), newQQNum);
            JsonObjectRequest changePasswordRequest = new JsonObjectRequest(Request.Method.PUT,
                    UrlManager.CHANGE_QQ_NUM_URL, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        System.out.println(response.toString());

                        if (response.getString("status").equals("0")) {
                            // progressbar gone
                            ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                        } else {
                            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                            EtalkFragmentManager.moveFragment(context, R.id.profilePage,
                                    changeQQFragment, userProfileFragment);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", EtalkSharedPreference.getPrefToken(context));
                    headers.put("Content-type", "application/json;charset=UTF-8");
                    return headers;
                }
            };
            requestQueue.add(changePasswordRequest);
        }
    }


    @Override
    public void onFragmentInteraction(int viewID) {
        switch (viewID) {
            case R.id.backBtn:
                backToMainScreen();
                break;
            case R.id.changePassword:
                EtalkFragmentManager.moveFragment(context, R.id.profilePage,
                        userProfileFragment, changePasswordFragment);
                break;
            case R.id.changeQQ:
                EtalkFragmentManager.moveFragment(context, R.id.profilePage,
                        userProfileFragment, changeQQFragment);
                break;
            case R.id.changePhoneNum:
                EtalkFragmentManager.moveFragment(context, R.id.profilePage,
                        userProfileFragment, changePhoneNumFragment);
                break;
            case R.id.logout:
                logout();
                break;
            default:
                break;
        }
    }

    @Override
    public void onChangePasswordInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                EtalkFragmentManager.moveFragment(context, R.id.profilePage,
                        changePasswordFragment, userProfileFragment);
                break;
            case R.id.saveNewPassword:
                saveNewPassword();
                break;
            case R.id.backToMainScreen:
                backToMainScreen();
                break;
            default:
                break;
        }
    }

    @Override
    public void onChangePhoneNumInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                EtalkFragmentManager.moveFragment(context, R.id.profilePage, changePhoneNumFragment, userProfileFragment);
                break;
            case R.id.saveNewPhoneNum:
                saveNewPhoneNum();
                break;
            case R.id.backToMainScreen:
                backToMainScreen();
                break;
            default:
                break;
        }

    }

    @Override
    public void onSaveQQClick(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                EtalkFragmentManager.moveFragment(context, R.id.profilePage, changeQQFragment, userProfileFragment);
                break;
            case R.id.saveNewQQNum:
                saveNewQQNum();
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
