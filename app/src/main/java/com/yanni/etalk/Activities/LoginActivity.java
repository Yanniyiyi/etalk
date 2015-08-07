package com.yanni.etalk.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Fragments.FindPasswordFragment;
import com.yanni.etalk.Fragments.LoginFragment;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.ErrorHelper;
import com.yanni.etalk.Utilities.EtalkFragmentManager;
import com.yanni.etalk.Utilities.EtalkSharedPreference;
import com.yanni.etalk.Utilities.JsonObjectGenerator;
import com.yanni.etalk.Utilities.UrlManager;
import com.yanni.etalk.Utilities.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


public class LoginActivity extends Activity implements
        LoginFragment.OnLoginInteractionListener,
        FindPasswordFragment.OnFindPasswordInteractionListener {

    final String URL = "http://www.etalk365.mobi:8080/api/v1/user_login";
    RequestQueue requestQueue;
    Button loginBtn;
    EditText username;
    EditText password;
    Context context;
    TextView forgetPassword;
    private LoginFragment loginFragment;
    private FindPasswordFragment findPasswordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        requestQueue = VolleySingleton.getInstance(LoginActivity.this).getRequestQueue();

        loginFragment = new LoginFragment();
        findPasswordFragment = new FindPasswordFragment();
        EtalkFragmentManager.addFragment(context, R.id.loginBody, loginFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    public void onLoginInteraction(int viewId) {
        switch (viewId) {
            case R.id.loginBtn:
                login();
                break;
            case R.id.forgetPassword:
                EtalkFragmentManager.moveFragment(context, R.id.loginBody, loginFragment, findPasswordFragment);
                break;
            case R.id.back_to_main_page:
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
        }
    }

    private void login() {

        username = (EditText) findViewById(R.id.loginUserNameEdittext);
        password = (EditText) findViewById(R.id.loginPasswordEdittext);

        String userName = username.getText().toString();
        String passWord = password.getText().toString();

        if (userName.isEmpty()) {
            Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (passWord.isEmpty()) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();

            progressDialog.show();
            JSONObject obj = JsonObjectGenerator.createLoginJsonObject(username.getText().toString(), password.getText().toString());
            RequestQueue requestQueue = VolleySingleton.getInstance(LoginActivity.this).getRequestQueue();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        System.out.println(response.toString());

                        if (response.getString("status").equals("0")) {

                            ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                        } else {
                            JSONObject userData = response.getJSONObject("data");
                            EtalkSharedPreference.setPrefLoginState(context, true);
                            EtalkSharedPreference.setPrefUserId(context, userData.getString("user_id"));
                            EtalkSharedPreference.setPrefUserName(context, userData.getString("user_name"));
                            EtalkSharedPreference.setPrefUserLevel(context, userData.getString("user_level"));
                            EtalkSharedPreference.setPrefUserScore(context, userData.getString("user_score"));
                            EtalkSharedPreference.setPrefUserNo(context, userData.getString("user_no"));
                            EtalkSharedPreference.setPrefToken(context, response.getString("token"));
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
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-type", "application/json;charset=UTF-8");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onFindPasswordInteraction(int viewId) {
        switch (viewId) {
            case R.id.backBtn:
                EtalkFragmentManager.moveFragment(context, R.id.loginBody, findPasswordFragment, loginFragment);
                break;
            case R.id.findPassword:
                findPassword();
                break;
            case R.id.backToMainScreen:
                backToMainScreen();
            default:
                break;
        }
    }

    private void backToMainScreen() {
        Intent returnIntent = getIntent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    private void findPassword() {
        String registerEmail = ((TextView) findViewById(R.id.findPasswordEmail))
                .getText().toString();
        if (registerEmail.isEmpty()) {
            Toast.makeText(context, "请输入注册邮箱", Toast.LENGTH_SHORT).show();
        } else {
            final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();

            progressDialog.show();
            JSONObject obj = JsonObjectGenerator.createFindPasswordJsonObject(registerEmail);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlManager.FIND_PASSWORD_URL, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                System.out.println(response.toString());

                                if (response.getString("status").equals("0")) {
                                    // progressbar gone

                                    ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                                } else {
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
                //            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("username","lu10");
//                params.put("password","123456");
//                return params;
//            }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-type", "application/json;charset=UTF-8");
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);
        }

    }
}
