package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanni.etalk.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AfterLoginHeaderFragment.OnSettingBtnClickListener} interface
 * to handle interaction events.
 * Use the {@link AfterLoginHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AfterLoginHeaderFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_NAME = "user_name";
    private static final String USER_ID = "user_ID";
    private static final String USER_LEVEL = "user_level";
    private static final String USER_SCORE = "user_score";


    // TODO: Rename and change types of parameters
    private String userName;
    private String userID;
    private String userLevel;
    private String userScore;

    private OnSettingBtnClickListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userName  Parameter 1.
     * @param userID    Parameter 2.
     * @param userLevel Parameter 3.
     * @param userScore Parameter 4.
     * @return A new instance of fragment AfterLoginHeaderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AfterLoginHeaderFragment newInstance(String param1, String param2,
                                                       String param3, String param4) {
        AfterLoginHeaderFragment fragment = new AfterLoginHeaderFragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, param1);
        args.putString(USER_ID, param2);
        args.putString(USER_LEVEL, param3);
        args.putString(USER_SCORE, param4);
        fragment.setArguments(args);
        return fragment;
    }

    public AfterLoginHeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString(USER_NAME);
            userID = getArguments().getString(USER_ID);
            userLevel = getArguments().getString(USER_LEVEL);
            userScore = getArguments().getString(USER_SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View headerView = inflater.inflate(R.layout.fragment_after_login_header, container, false);

        TextView userNameText = (TextView) headerView.findViewById(R.id.userMainScreenName);
        userNameText.setText(userName);

        TextView userNoText = (TextView) headerView.findViewById(R.id.userMainScreenID);
        userNoText.setText("学号: " + userID);

        TextView userLevelText = (TextView) headerView.findViewById(R.id.userMainScreenLevel);
        userLevelText.setText("等级: " + userLevel);

        TextView userScoreText = (TextView) headerView.findViewById(R.id.userMainScreenScore);
        userScoreText.setText("积分: " + userScore);

        ImageView imageView = (ImageView) headerView.findViewById(R.id.settingBtn);
        imageView.setOnClickListener(this);

        return headerView;
    }

    //    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onSettingBtnClick();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSettingBtnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSettingBtnClickListener");
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
            case R.id.settingBtn:
                onButtonPressed();
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
    public interface OnSettingBtnClickListener {
        // TODO: Update argument type and name
        void onSettingBtnClick();
    }

}
