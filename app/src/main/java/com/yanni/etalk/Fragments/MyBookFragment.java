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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Adapters.BookCourseListAdapter;
import com.yanni.etalk.Adapters.SectionedGridRecyclerViewAdapter;
import com.yanni.etalk.Entities.BookedCourse;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.DateUtility;
import com.yanni.etalk.Utilities.ErrorHelper;
import com.yanni.etalk.Utilities.EtalkSharedPreference;
import com.yanni.etalk.Utilities.JsonObjectGenerator;
import com.yanni.etalk.Utilities.UrlManager;
import com.yanni.etalk.Utilities.VolleySingleton;
import com.yanni.etalk.Views.RecyclerViewEmptySupport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyBookFragment.OnMyBookFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBookFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;
    private ArrayList<BookedCourse> bookedCourseArrayList;
    private LinkedHashMap<String, ArrayList<BookedCourse>> map;
    private RecyclerViewEmptySupport recyclerView;
    private View myBookCourseListView;

    private OnMyBookFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyBookFragment newInstance(String param1, String param2) {
        MyBookFragment fragment = new MyBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bookedCourseArrayList = new ArrayList<>();
        this.myBookCourseListView = inflater.inflate(R.layout.fragment_my_book, container, false);

        ImageView backBtn = (ImageView) myBookCourseListView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) myBookCourseListView.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("我的预约");


        recyclerView = (RecyclerViewEmptySupport) myBookCourseListView.findViewById(R.id.book_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setEmptyView(myBookCourseListView.findViewById(R.id.empty_view));
        fetchBookCourses("1", "30");

        Button bookClass = (Button)myBookCourseListView.findViewById(R.id.go_to_book);
        bookClass.setOnClickListener(this);
        return myBookCourseListView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onMyBookFragmentInteraction(viewId);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            mListener = (OnMyBookFragmentInteractionListener) activity;
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
    public interface OnMyBookFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMyBookFragmentInteraction(int viewId);
    }


    private void fetchBookCourses(String page, String pageSize) {
        final ACProgressFlower dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();

        dialog.show();
        JSONObject obj = JsonObjectGenerator.createFetchBookCourseJsonObject(EtalkSharedPreference.getPrefUserId(context),
                page, pageSize);
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                UrlManager.FETCH_BOOK_COURSE_URL, obj, new Response.Listener<JSONObject>() {
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
                            JSONObject item = (JSONObject) courses.get(i);
                            BookedCourse bookedCourse = new BookedCourse(item.getString("id"),
                                    item.getString("title"), item.getString("bespeak_date"),
                                    item.getString("bespeak_period"), item.getString("bespeak_tea_name"),
                                    item.getString("bespeak_time"));
                            bookedCourseArrayList.add(bookedCourse);
                        }

                        // your list of all people
                        map = new LinkedHashMap<String, ArrayList<BookedCourse>>();
                        int arrayLength = bookedCourseArrayList.size();
                        for (int i = 0; i < arrayLength; i++) {
                            BookedCourse bookedCourse = bookedCourseArrayList.get(i);
                            String key = bookedCourse.getBookDate();
                            if (map.get(key) == null) {
                                map.put(key, new ArrayList<BookedCourse>());
                            }
                            map.get(key).add(bookedCourse);
                        }
                        System.out.println(response.getJSONArray("data").length());
                        System.out.println(response.toString());
//                        toCommentListFragment = ToCommentListFragment.newInstance(toCommentCourseArrayList);
//                        EtalkFragmentManager.addFragment(context, R.id.toCommentCourses, toCommentListFragment);


                        //RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.list);

                        //Your RecyclerView.Adapter
                        BookCourseListAdapter bookCourseListAdapter = new BookCourseListAdapter(context, bookedCourseArrayList);

                        //This is the code to provide a sectioned grid
                        List<SectionedGridRecyclerViewAdapter.Section> sections =
                                new ArrayList<SectionedGridRecyclerViewAdapter.Section>();

                        //Sections

                        Set set = map.entrySet();
                        // Get an iterator
                        Iterator i = set.iterator();
                        // Display elements
                        int startIndex = 0;
                        while (i.hasNext()) {
                            Map.Entry me = (Map.Entry) i.next();
                            sections.add(new SectionedGridRecyclerViewAdapter.Section(startIndex,
                                    DateUtility.calculateWeekDay(me.getKey().toString().trim())));
                            startIndex = startIndex + ((ArrayList) me.getValue()).size();
                            System.out.println(me.getKey().toString());
                        }

                        //Add your adapter to the sectionAdapter
                        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
                        SectionedGridRecyclerViewAdapter mSectionedAdapter = new
                                SectionedGridRecyclerViewAdapter(context, R.layout.section, R.id.section_text, recyclerView, bookCourseListAdapter);
                        mSectionedAdapter.setSections(sections.toArray(dummy));

                        //Apply this adapter to the RecyclerView
                        recyclerView.setAdapter(mSectionedAdapter);
                        Toast.makeText(context,"nothing",Toast.LENGTH_SHORT).show();

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
