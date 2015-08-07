package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.yanni.etalk.Adapters.BookClassTimeListAdapter;
import com.yanni.etalk.Entities.EtalkClass;
import com.yanni.etalk.Entities.EtalkPackage;
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
 * {@link BookClassDetailFragment.OnBookClassDetailInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookClassDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookClassDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ETALK_PACKAGE = "etalk_package";
    private static final String DATE = "date";

    // TODO: Rename and change types of parameters
    private String strDate;
    private EtalkPackage etalkPackage;
    private RecyclerView recyclerView;
    private View bookClassTimeListView;
    private BookClassTimeListAdapter bookClassTimeListAdapter;

    private Context context;

    private OnBookClassDetailInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookClassDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookClassDetailFragment newInstance(EtalkPackage etalkPackage, String date) {
        BookClassDetailFragment fragment = new BookClassDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ETALK_PACKAGE, etalkPackage);
        args.putString(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }


    public BookClassDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.etalkPackage = getArguments().getParcelable(ETALK_PACKAGE);
            this.strDate = getArguments().getString(DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        bookClassTimeListView = inflater.inflate(R.layout.fragment_book_class_detail, container, false);


        ImageView backBtn = (ImageView) bookClassTimeListView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) bookClassTimeListView.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("预约课程");

        ImageView backToMain = (ImageView) bookClassTimeListView.findViewById(R.id.backToMainScreen);
        backToMain.setBackgroundResource(R.mipmap.ic_home);
        backToMain.setOnClickListener(this);


        recyclerView = (RecyclerView) bookClassTimeListView.findViewById(R.id.book_class_time_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        TextView lastDay = (TextView) bookClassTimeListView.findViewById(R.id.last_day);
        TextView nextDay = (TextView) bookClassTimeListView.findViewById(R.id.next_day);
        TextView currentDay = (TextView) bookClassTimeListView.findViewById(R.id.current_date);
        currentDay.setText(DateUtility.bookClassFormat(strDate));

        lastDay.setOnClickListener(this);
        nextDay.setOnClickListener(this);
        currentDay.setOnClickListener(this);
        fetchBookClassTime(strDate);
        return bookClassTimeListView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId, String date) {
        if (mListener != null) {
            mListener.onBookClassDetailInteraction(viewId, date);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            mListener = (OnBookClassDetailInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBookClassDetailInteractionListener");
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
            case R.id.last_day:
                strDate = DateUtility.getLastDay(strDate);
                System.out.println(strDate + "start");
                if (DateUtility.lessThanCurrentDate()) {
                    Toast.makeText(context, "无法预订昨天的课程", Toast.LENGTH_SHORT).show();
                    System.out.println(strDate + "end1");
                    strDate = DateUtility.getNextDay(strDate);
                    System.out.println(strDate + "end2");
                } else {
                    fetchBookClassTime(strDate);
                    changeDisplayDate(DateUtility.bookClassFormat(strDate));
                }
                break;
            case R.id.next_day:
                strDate = DateUtility.getNextDay(strDate);
                System.out.println(strDate + "");
                fetchBookClassTime(strDate);
                changeDisplayDate(DateUtility.bookClassFormat(strDate));
                break;
            default:
                onButtonPressed(view.getId(), strDate);
                break;
        }
    }


    private void changeDisplayDate(String date) {
        TextView currentDay = (TextView) bookClassTimeListView.findViewById(R.id.current_date);
        currentDay.setText(date);
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
    public interface OnBookClassDetailInteractionListener {
        // TODO: Update argument type and name
        void onBookClassDetailInteraction(int viewId, String date);
    }


    private void fetchBookClassTime(final String date) {

        final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();

        progressDialog.show();

        JSONObject obj =
                JsonObjectGenerator.createFetchBookClassTimeJsonObject(EtalkSharedPreference.getPrefUserId(context), etalkPackage.getProductId(),
                        date);
        final RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                UrlManager.FETCH_BOOK_CLASS_TIME, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    System.out.println(response.toString());
                    if (response.getString("status").equals("0")) {
                        // progressbar gone
                        System.out.println(response.getJSONArray("data").length());
                        ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                    } else {
                        ArrayList<EtalkClass> arrayList = new ArrayList<>();
                        JSONArray courses = response.getJSONArray("data");
                        System.out.println(courses.toString());
                        if (!(courses.toString().equals("[null]"))) {
                            int length = courses.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject course = (JSONObject) courses.get(i);
                                EtalkClass etalkClass = new EtalkClass(etalkPackage.getPackageId(), etalkPackage.getLm(),
                                        course.getString("period"), course.getString("begin"), course.getString("status"));
                                arrayList.add(etalkClass);
                            }

                        } else {
                        }
                        if (bookClassTimeListAdapter != null) {
                            System.out.println("update invoked");
                            bookClassTimeListAdapter.updateList(arrayList, date);
                        } else {
                            System.out.println("initialize invoked");
                            bookClassTimeListAdapter = new BookClassTimeListAdapter(context, arrayList, date);
                        }
                        recyclerView.setAdapter(bookClassTimeListAdapter);

                        System.out.println(response.getJSONArray("data").length());
                        System.out.println(response.toString());
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
