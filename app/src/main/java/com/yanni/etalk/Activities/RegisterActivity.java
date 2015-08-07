package com.yanni.etalk.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.ErrorHelper;
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


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {

    private Button registerBtn;
    private EditText registerUserName;
    private EditText registerPassword;
    private EditText registerEmail;
    private EditText registerPhoneNum;
    private EditText registerInviteCode;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.getMenu().clear();

        context = this;

        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("学员注册");

        ImageView backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);


        registerUserName = (EditText) findViewById(R.id.registerUserName);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerPhoneNum = (EditText) findViewById(R.id.registerPhoneNum);
        registerInviteCode = (EditText) findViewById(R.id.registerInviteCode);

        registerBtn = (Button) findViewById(R.id.confirmRegisterBtn);
        registerBtn.setOnClickListener(this);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.confirmRegisterBtn:
                register();
                break;
            default:
                break;

        }
    }

    private void register() {
        String userName = registerUserName.getText().toString();
        String password = registerPassword.getText().toString();
        String phoneNum = registerPhoneNum.getText().toString();
        String email = registerEmail.getText().toString();
        String inviteCode = registerInviteCode.getText().toString();


        if (userName.isEmpty()) {
            Toast.makeText(context, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
        } else if (!ValidateUtility.Email(email)) {
            Toast.makeText(context, "邮箱地址无效", Toast.LENGTH_SHORT).show();
        } else if (!ValidateUtility.PhoneNum(phoneNum)) {
            Toast.makeText(context, "手机号码无效", Toast.LENGTH_SHORT).show();
        } else {
            final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.DKGRAY).build();

            progressDialog.show();
            JSONObject obj = JsonObjectGenerator.createRegisterJsonObject(userName, password,
                    email, phoneNum, inviteCode);
            RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    UrlManager.REGISTER_URL, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        System.out.println(response.getString("status"));
                        if (response.getString("status").equals("0")) {
                            // progressbar gone
                            System.out.println(response.toString());
                            ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                        } else {
                            System.out.println(response.toString());
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
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-type", "application/json;charset=UTF-8");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        }

    }

}
