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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Adapters.SimpleAdapter;
import com.yanni.etalk.Entities.ToCommentCourse;
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
import java.util.LinkedHashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToCommentListFragment.OnCommentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ToCommentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToCommentListFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COURSE_LIST = "course_list";

    // TODO: Rename and change types of parameters
    private ArrayList<ToCommentCourse> toCommentCourseArrayList;
    private RecyclerView recyclerView;
    private SimpleAdapter simpleAdapter;
    private Context context;
    private LinkedHashMap<String, ArrayList<ToCommentCourse>> map;
    private OnCommentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodCommentListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToCommentListFragment newInstance() {
        ToCommentListFragment fragment = new ToCommentListFragment();
        Bundle args = new Bundle();
        // args.putParcelableArrayList(COURSE_LIST, toCommentCourseArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    public ToCommentListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  toCommentCourseArrayList = getArguments().getParcelableArrayList(COURSE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View courseListView = inflater.inflate(R.layout.fragment_to_comment_list, container, false);
        toCommentCourseArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) courseListView.findViewById(R.id.toCommentCourseList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        fetchToComment("1", "100");

        ImageView backBtn = (ImageView) courseListView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) courseListView.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("待评价课程");

        return courseListView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onButtonInteraction(viewId);
        }
    }

    public void onItemClicked(ToCommentCourse toCommentCourse) {
        if (mListener != null) {
            mListener.onItemInteraction(toCommentCourse);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            mListener = (OnCommentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnCommentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        onButtonPressed(view.getId());
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
    public interface OnCommentInteractionListener {
        // TODO: Update argument type and name
        void onButtonInteraction(int viewId);

        void onItemInteraction(ToCommentCourse toCommentCourse);
    }


    private void fetchToComment(String page, String pageSize) {


        final ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();

        dialog.show();
        JSONObject obj = JsonObjectGenerator.createFetchToCommentJsonObject(EtalkSharedPreference.getPrefUserId(context),
                page, pageSize);
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                UrlManager.FETCH_TOCOMMENT_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                try {
                    System.out.println(response.getString("status"));
                    if (response.getString("status").equals("0")) {
                        // progressbar gone
                        System.out.println(response.getJSONArray("data").length());
                        ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                    } else {
                        JSONArray courses = response.getJSONArray("data");
                        int length = courses.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject course = (JSONObject) courses.get(i);
                            ToCommentCourse toCommentCourse = new ToCommentCourse(course.getString("bespeak_date"),
                                    course.getString("bespeak_period"), course.getString("id"), course.getString("bespeak_tea_name"));
                            toCommentCourseArrayList.add(toCommentCourse);
                        }

                        // your list of all people
                        map = new LinkedHashMap<>();
                        int arrayLength = toCommentCourseArrayList.size();
                        for (int i = 0; i < arrayLength; i++) {
                            ToCommentCourse toCommentCourse = toCommentCourseArrayList.get(i);
                            String key = toCommentCourse.getDate();
                            if (map.get(key) == null) {
                                map.put(key, new ArrayList<ToCommentCourse>());
                            }
                            map.get(key).add(toCommentCourse);
                        }
                        System.out.println(response.getJSONArray("data").length());
                        System.out.println(response.toString());
//                        toCommentListFragment = ToCommentListFragment.newInstance(toCommentCourseArrayList);
//                        EtalkFragmentManager.addFragment(context, R.id.toCommentCourses, toCommentListFragment);

                        simpleAdapter = new SimpleAdapter(context, map);
                        recyclerView.setAdapter(simpleAdapter);

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
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", EtalkSharedPreference.getPrefToken(context));
                headers.put("Content-type", "application/json;charset=UTF-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


}
