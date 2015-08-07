package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.EtalkSharedPreference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment implements View.OnClickListener {
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
    private Context context;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param Parameter 1.
     * @param param2    Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String userName, String userID,
                                                  String userLevel, String userScore) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, userName);
        args.putString(USER_ID, userID);
        args.putString(USER_LEVEL, userLevel);
        args.putString(USER_SCORE, userScore);
        fragment.setArguments(args);
        return fragment;
    }

    public UserProfileFragment() {
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
        View profileView = inflater.inflate(R.layout.fragment_user_profile, container, false);


        TextView userName = (TextView) profileView.findViewById(R.id.profileName);
        userName.setText(EtalkSharedPreference.getPrefUserName(context));

        TextView userNum = (TextView) profileView.findViewById(R.id.profileNo);
        userNum.setText("学号: " + EtalkSharedPreference.getPrefUserId(context));

        TextView userScore = (TextView) profileView.findViewById(R.id.profileScore);
        userScore.setText("积分: " + EtalkSharedPreference.getPrefUserScore(context));

        TextView userLevel = (TextView) profileView.findViewById(R.id.profileLevel);
        userLevel.setText("等级: " + EtalkSharedPreference.getPrefUserLevel(context));


        TextView title = (TextView) profileView.findViewById(R.id.toolbar_title);
        title.setText("个人中心");
        TextView changePassword = (TextView) profileView.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(this);
        TextView changePhoneNum = (TextView) profileView.findViewById(R.id.changePhoneNum);
        changePhoneNum.setOnClickListener(this);
        TextView changeQQ = (TextView) profileView.findViewById(R.id.changeQQ);
        changeQQ.setOnClickListener(this);
        TextView logout = (TextView) profileView.findViewById(R.id.logout);
        logout.setOnClickListener(this);

        ImageView backBtn = (ImageView) profileView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);


        return profileView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onViewPressed(int viewID) {
        if (mListener != null) {
            mListener.onFragmentInteraction(viewID);
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
        onViewPressed(view.getId());
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
        void onFragmentInteraction(int viewID);
    }

}
