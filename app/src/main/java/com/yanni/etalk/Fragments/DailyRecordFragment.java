package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Adapters.DailyRecordListAdapter;
import com.yanni.etalk.Entities.DailyRecord;
import com.yanni.etalk.Entities.MonthlyRecord;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.ErrorHelper;
import com.yanni.etalk.Utilities.EtalkSharedPreference;
import com.yanni.etalk.Utilities.JsonObjectGenerator;
import com.yanni.etalk.Utilities.UrlManager;
import com.yanni.etalk.Utilities.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DailyRecordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DailyRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyRecordFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECORD = "record";

    // TODO: Rename and change types of parameters
    private MonthlyRecord monthlyRecord;
    private Context context;
    private RequestQueue requestQueue;
    private ArrayList<DailyRecord> dailyRecordArrayList;
    private RecyclerView recyclerView;
    private View dailyRecordView;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecordDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyRecordFragment newInstance(MonthlyRecord monthlyRecord) {
        DailyRecordFragment fragment = new DailyRecordFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECORD, monthlyRecord);
        fragment.setArguments(args);
        return fragment;
    }

    public DailyRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            monthlyRecord = getArguments().getParcelable(RECORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

        dailyRecordView = inflater.inflate(R.layout.fragment_daily_record, container, false);

        recyclerView = (RecyclerView) dailyRecordView.findViewById(R.id.daily_record_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        ImageView backBtn = (ImageView) dailyRecordView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) dailyRecordView.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("课程信息");

        ImageView backToMain = (ImageView) dailyRecordView.findViewById(R.id.backToMainScreen);
        backToMain.setBackgroundResource(R.mipmap.ic_home);
        backToMain.setOnClickListener(this);


        fetchDailyRecord();

        return dailyRecordView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onDailyRecordToolbarClick(view.getId());
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();

        void onDailyRecordToolbarClick(int viewId);
    }

    private void fetchDailyRecord() {
        final ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();

        dialog.show();
        System.out.println("fetch called");
        dailyRecordArrayList = new ArrayList<>();
        JSONObject obj = JsonObjectGenerator.createFetchDailyRecordJsonObject(EtalkSharedPreference.getPrefUserName(context),
                monthlyRecord.getCourseTime());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                UrlManager.FETCH_DAILY_RECORD_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    System.out.println(response.toString());
                    if (response.getString("status").equals("0")) {
                        // progressbar gone
                        System.out.println(response.getJSONArray("data").length());
                        ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                    } else {
                        JSONArray courses = response.getJSONArray("data");
                        int length = courses.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject course = (JSONObject) courses.get(i);
                            DailyRecord dailyRecord = new DailyRecord(course.getString("id"),
                                    course.getString("title"), course.getString("bespeak_tea_name"),
                                    course.getString("bespeak_time"), course.getString("tea_comment_text"),
                                    course.getString("tea_comment_rank"), course.getString("stu_comment_text"),
                                    course.getString("stu_comment_rank"));
                            dailyRecordArrayList.add(dailyRecord);
                            dailyRecordArrayList.add(dailyRecord);
                        }
                        System.out.println(response.getJSONArray("data").length());
                        System.out.println(response.toString());
                        DailyRecordListAdapter dailyRecordListAdapter = new DailyRecordListAdapter(context, dailyRecordArrayList);
                        recyclerView.setAdapter(dailyRecordListAdapter);
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


}
