package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Adapters.MonthlyRecordListAdapter;
import com.yanni.etalk.Entities.MonthlyRecord;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.DateUtility;
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
 * {@link MonthlyRecordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonthlyRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyRecordFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private View monthRecordView;
    private String month;
    private MonthlyRecordListAdapter monthlyRecordListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MonthlyRecord> monthlyRecordArrayList;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyRecordFragment newInstance(String param1, String param2) {
        MonthlyRecordFragment fragment = new MonthlyRecordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MonthlyRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        monthRecordView = inflater.inflate(R.layout.fragment_monthly_record, container, false);
        month = DateUtility.getCurrentMonth();

        ImageView backBtn = (ImageView) monthRecordView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) monthRecordView.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("上课记录");


        ImageView lastMonth = (ImageView) monthRecordView.findViewById(R.id.last_month);
        lastMonth.setOnClickListener(this);

        ImageView nextMonth = (ImageView) monthRecordView.findViewById(R.id.next_month);
        nextMonth.setOnClickListener(this);

        TextView currentMonth = (TextView) monthRecordView.findViewById(R.id.current_month);
        currentMonth.setText(month);

        recyclerView = (RecyclerView) monthRecordView.findViewById(R.id.month_record_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        fetchRecord();


        final GestureDetector mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    int i = recyclerView.getChildLayoutPosition(child);
                    onItemClicked(monthlyRecordArrayList.get(i));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });


        return monthRecordView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onButtonInteraction(viewId);
        }
    }

    public void onItemClicked(MonthlyRecord monthlyRecord) {
        if (mListener != null) {
            mListener.onItemInteraction(monthlyRecord);
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
        switch (view.getId()) {
            case R.id.last_month:
                month = DateUtility.getLastMonth();
                if (DateUtility.lessThanCurrentMonth()) {
                    changeDate(month);
                    fetchRecord();
                } else {
                    month = DateUtility.getNextMonth();
                    Toast.makeText(context, "现在就是" + month, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.next_month:
                month = DateUtility.getNextMonth();
                if (DateUtility.lessThanCurrentMonth()) {
                    changeDate(month);
                    fetchRecord();
                } else {
                    month = DateUtility.getLastMonth();
                    Toast.makeText(context, "现在就是" + month, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.backBtn:
                onButtonPressed(view.getId());
                break;
            default:
                break;
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
        void onButtonInteraction(int viewId);

        void onItemInteraction(MonthlyRecord monthlyRecord);
    }


    private void changeDate(String date) {
        TextView currentDate = (TextView) monthRecordView.findViewById(R.id.current_month);
        currentDate.setText(date);
    }


    private void fetchRecord() {
        final ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();

        dialog.show();
        monthlyRecordArrayList = new ArrayList<>();
        JSONObject obj =
                JsonObjectGenerator.createFetchMonthRecordJsonObject(EtalkSharedPreference.getPrefUserName(context),
                        month);
        final RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                UrlManager.FETCH_MONTH_RECORD_URL, obj, new Response.Listener<JSONObject>() {
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
                        System.out.println(courses.toString());
                        if (!(courses.toString().equals("[null]"))) {
                            int length = courses.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject recordItem = (JSONObject) courses.get(i);
                                MonthlyRecord monthlyRecord = new MonthlyRecord(recordItem.getString("id"),
                                        recordItem.getString("bespeak_time"));
                                monthlyRecordArrayList.add(monthlyRecord);
                            }

                        } else {
                            Toast.makeText(context, "本月无上课记录", Toast.LENGTH_SHORT).show();
                        }
                        System.out.println(response.getJSONArray("data").length());
                        System.out.println(response.toString());
                        monthlyRecordListAdapter = new MonthlyRecordListAdapter(context, monthlyRecordArrayList);
                        recyclerView.setAdapter(monthlyRecordListAdapter);
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
                headers.put("Authorization", EtalkSharedPreference.getPrefToken(context));
                headers.put("Content-type", "application/json;charset=UTF-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

}
