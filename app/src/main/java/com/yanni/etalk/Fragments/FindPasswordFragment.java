package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanni.etalk.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindPasswordFragment.OnFindPasswordInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindPasswordFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFindPasswordInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindPasswordFragment newInstance(String param1, String param2) {
        FindPasswordFragment fragment = new FindPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FindPasswordFragment() {
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
        View findPasswordView = inflater.inflate(R.layout.fragment_find_password, container, false);

        TextView title = (TextView) findPasswordView.findViewById(R.id.toolbar_title);
        title.setText("找回密码");

        ImageView backBtn = (ImageView) findPasswordView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        ImageView backToMainScreenBtn = (ImageView) findPasswordView.findViewById(R.id.backToMainScreen);
        backToMainScreenBtn.setOnClickListener(this);

        Button findPassword = (Button) findPasswordView.findViewById(R.id.findPassword);
        findPassword.setOnClickListener(this);

        return findPasswordView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onFindPasswordInteraction(viewId);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFindPasswordInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFindPasswordInteractionListener");
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
    public interface OnFindPasswordInteractionListener {
        // TODO: Update argument type and name
        void onFindPasswordInteraction(int viewId);
    }

}
