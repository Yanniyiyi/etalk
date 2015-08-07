package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yanni.etalk.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BeforeLoginFooterFragment.OnLoginFooterInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BeforeLoginFooterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeforeLoginFooterFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int LOGIN_TAG = 1;
    private static final int REGISTER_TAG = 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnLoginFooterInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeforeLoginFooterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeforeLoginFooterFragment newInstance(String param1, String param2) {
        BeforeLoginFooterFragment fragment = new BeforeLoginFooterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BeforeLoginFooterFragment() {
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
        View footerView = inflater.inflate(R.layout.fragment_before_login_footer, container, false);

        Button login = (Button) footerView.findViewById(R.id.login);
        login.setTag(LOGIN_TAG);

        Button register = (Button) footerView.findViewById(R.id.register);
        register.setTag(REGISTER_TAG);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        return footerView;
    }


    @Override
    public void onClick(View view) {
        onButtonPressed((int) view.getTag());
    }


    //    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int btnTag) {
        if (mListener != null) {
            mListener.onLoginFooterInteraction(btnTag);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnLoginFooterInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnLoginFooterInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnLoginFooterInteractionListener {
        // TODO: Update argument type and name
        void onLoginFooterInteraction(int btnTag);
    }

}
