package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yanni.etalk.Entities.DailyRecord;
import com.yanni.etalk.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowTeacherStudentCommentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowTeacherStudentCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowTeacherStudentCommentFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DAILY_RECORD = "daily_record";
    private static final String COMMENT_TYPE = "comment_type";
    private static final int TEACHER_COMMENT = 0;
    private static final int STUDENT_COMMENT = 1;

    // TODO: Rename and change types of parameters
    private DailyRecord dailyRecord;

    private OnShowCommentFragmentInteractionListener mListener;
    private int commentType;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentCommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowTeacherStudentCommentFragment newInstance(DailyRecord dailyRecord, int commentType) {
        ShowTeacherStudentCommentFragment fragment = new ShowTeacherStudentCommentFragment();
        Bundle args = new Bundle();
        args.putParcelable(DAILY_RECORD, dailyRecord);
        args.putInt(COMMENT_TYPE, commentType);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowTeacherStudentCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dailyRecord = getArguments().getParcelable(DAILY_RECORD);
            commentType = getArguments().getInt(COMMENT_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View commentView = inflater.inflate(R.layout.fragment_student_comment, container, false);

        TextView studentComment = (TextView) commentView.findViewById(R.id.student_comment);

        RatingBar studentRating = (RatingBar) commentView.findViewById(R.id.student_comment_rating);


        ImageView backBtn = (ImageView) commentView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) commentView.findViewById(R.id.toolbar_title);


        ImageView backToMain = (ImageView) commentView.findViewById(R.id.backToMainScreen);
        backToMain.setBackgroundResource(R.mipmap.ic_home);
        backToMain.setOnClickListener(this);

        TextView commentSource = (TextView) commentView.findViewById(R.id.comment_source);
        TextView commentTo = (TextView) commentView.findViewById(R.id.comment_to);


        if (commentType == TEACHER_COMMENT) {
            studentComment.setText(Html.fromHtml(dailyRecord.getTeacherComment(), null, null));
            studentComment.setMovementMethod(new ScrollingMovementMethod());
            studentRating.setIsIndicator(true);
            studentRating.setRating(Float.parseFloat(dailyRecord.getTeacherRank()));
            toolBarTitle.setText("老师的评价");
            commentSource.setText("老师的评价");
            commentTo.setText("给我的评价");
        }

        if (commentType == STUDENT_COMMENT) {
            studentComment.setText(Html.fromHtml(dailyRecord.getStudentComment(), null, null));
            studentComment.setMovementMethod(new ScrollingMovementMethod());
            studentRating.setIsIndicator(true);
            studentRating.setRating(Float.parseFloat(dailyRecord.getStudentRank()));
            toolBarTitle.setText("我的评价");
            commentSource.setText("我的评价");
            commentTo.setText("给老师的评价");
        }

        return commentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onShowCommentToolbarClick(viewId);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnShowCommentFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShowCommentFragmentInteractionListener");
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
    public interface OnShowCommentFragmentInteractionListener {
        // TODO: Update argument type and name
        void onShowCommentToolbarClick(int viewId);
    }

}
