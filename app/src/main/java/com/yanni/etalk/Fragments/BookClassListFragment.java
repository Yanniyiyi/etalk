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
import com.yanni.etalk.Adapters.BookClassListAdapter;
import com.yanni.etalk.Entities.EtalkPackage;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.DividerItemDecoration;
import com.yanni.etalk.Utilities.ErrorHelper;
import com.yanni.etalk.Utilities.EtalkSharedPreference;
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
 * {@link BookClassListFragment.OnBookClassListInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookClassListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookClassListFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context;

    private OnBookClassListInteractionListener mListener;
    private ArrayList<EtalkPackage> etalkPackageArrayList;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookClassListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookClassListFragment newInstance(String param1, String param2) {
        BookClassListFragment fragment = new BookClassListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BookClassListFragment() {
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
        View bookClassListView = inflater.inflate(R.layout.fragment_book_class_list, container, false);

        TextView title = (TextView) bookClassListView.findViewById(R.id.toolbar_title);
        title.setText("选择课程套餐");

        ImageView backBtn = (ImageView) bookClassListView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        ImageView backToMainScreenBtn = (ImageView) bookClassListView.findViewById(R.id.backToMainScreen);
        backToMainScreenBtn.setOnClickListener(this);


        etalkPackageArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) bookClassListView.findViewById(R.id.book_class_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();


        fetchMyPackage();


        return bookClassListView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onBookClassListInteraction(viewId);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            mListener = (OnBookClassListInteractionListener) activity;
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
    public interface OnBookClassListInteractionListener {
        // TODO: Update argument type and name
        void onBookClassListInteraction(int viewId);
    }


    private void fetchMyPackage() {
        final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();

        progressDialog.show();
        JsonObjectRequest changePasswordRequest = new JsonObjectRequest(Request.Method.GET,
                UrlManager.FETCH_PACKAGE_URL + EtalkSharedPreference.getPrefUserId(context)
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    System.out.println(response.toString());

                    if (response.getString("status").equals("0")) {
                        ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                    } else {
                        JSONArray data = response.getJSONArray("data");
                        int length = data.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject item = data.getJSONObject(i);
                            EtalkPackage etalkPackage = new EtalkPackage(item.getString("product_title"),
                                    item.getString("wtime"), item.getString("wtime"),
                                    item.getString("product_lessionNum"), item.getString("lession_leaveNum"),
                                    item.getString("total_money"), item.getString("order_state"), item.getString("product_id"),
                                    item.getString("lm"), item.getString("id"));
                            etalkPackageArrayList.add(etalkPackage);
                        }
                        recyclerView.addItemDecoration(new DividerItemDecoration(getResources()));
                        BookClassListAdapter bookClassListAdapter = new BookClassListAdapter(context, etalkPackageArrayList);
                        recyclerView.setAdapter(bookClassListAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", EtalkSharedPreference.getPrefToken(context));
                return headers;
            }
        };
        requestQueue.add(changePasswordRequest);
    }

}
