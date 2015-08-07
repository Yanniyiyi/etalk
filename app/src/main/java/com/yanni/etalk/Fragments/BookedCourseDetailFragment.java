package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Entities.BookedCourse;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.DateUtility;
import com.yanni.etalk.Utilities.ErrorHelper;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookedCourseDetailFragment.OnBookedCourseDetailInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookedCourseDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookedCourseDetailFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BOOKED_COURSE = "booked_course";
    private Context context;
    // TODO: Rename and change types of parameters
    private BookedCourse bookedCourse;
    private RequestQueue requestQueue;

    private OnBookedCourseDetailInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookedCourseDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookedCourseDetailFragment newInstance(BookedCourse bookedCourse) {
        BookedCourseDetailFragment fragment = new BookedCourseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(BOOKED_COURSE, bookedCourse);
        fragment.setArguments(args);
        return fragment;
    }

    public BookedCourseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookedCourse = getArguments().getParcelable(BOOKED_COURSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();


        View bookCourseDetailView = inflater.inflate(R.layout.fragment_booked_course_detail, container, false);
        TextView courseTitle = (TextView) bookCourseDetailView.findViewById(R.id.booked_course_title);
        courseTitle.setText("学习课程： " + bookedCourse.getCourseTitle());
        TextView courseTime = (TextView) bookCourseDetailView.findViewById(R.id.booked_course_time);
        courseTime.setText("上课时间： " + bookedCourse.getBookDate() + " " + bookedCourse.getBookTime());
        TextView teacher = (TextView) bookCourseDetailView.findViewById(R.id.booed_course_teacher);
        teacher.setText("上课老师： " + bookedCourse.getTeacher());

        TextView cancelCourse = (TextView) bookCourseDetailView.findViewById(R.id.cancel_booked_course);
        cancelCourse.setOnClickListener(this);


        ImageView backBtn = (ImageView) bookCourseDetailView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) bookCourseDetailView.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("已约课程信息");

        ImageView backToMain = (ImageView) bookCourseDetailView.findViewById(R.id.backToMainScreen);
        backToMain.setBackgroundResource(R.mipmap.ic_home);
        backToMain.setOnClickListener(this);


        return bookCourseDetailView;
    }

    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onBookedCourseDetailInteraction(viewId);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            mListener = (OnBookedCourseDetailInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBookedCourseDetailInteractionListener");
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
            case R.id.cancel_booked_course:
                cancelBookedCourse();
                break;
            default:
                onButtonPressed(view.getId());
                break;
        }
    }

    private void cancelBookedCourse() {
        if (DateUtility.greaterThanCourseTime(bookedCourse.getBookDate(), bookedCourse.getBookTime())) {

            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_cant_cancel_course_alert);
            // set the custom dialog components - text, image and button
            final Button closeBtn = (Button) dialog.findViewById(R.id.close_alert);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            System.out.println(bookedCourse.getBookDate());
            System.out.println(bookedCourse.getBookTime());
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_cancel_course_confirm_alert);
            // set the custom dialog components - text, image and button
            final Button closeBtn = (Button) dialog.findViewById(R.id.close_confirm);
            final Button confirmBtn = (Button) dialog.findViewById(R.id.confirm_cancel_course);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(Color.WHITE)
                            .fadeColor(Color.DKGRAY).build();

                    progressDialog.show();
                    JSONObject obj = JsonObjectGenerator.createCancelCourseJsonObject(EtalkSharedPreference.getPrefUserId(context),
                            bookedCourse.getCourseId());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                            UrlManager.CANCEL_COURSE, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                System.out.println(response.toString());
                                if (response.getString("status").equals("0")) {


                                    ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                                } else {
                                    mListener.onCourseCanceledInteraction();
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
            dialog.show();


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
    public interface OnBookedCourseDetailInteractionListener {
        // TODO: Update argument type and name
        void onBookedCourseDetailInteraction(int viewId);

        void onCourseCanceledInteraction();
    }

}
