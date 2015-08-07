package com.yanni.etalk.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanni.etalk.Adapters.ToCommentAdapter;
import com.yanni.etalk.Entities.ToCommentCourse;
import com.yanni.etalk.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostCommentFragment.OnCommentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostCommentFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COURSES = "courses";


    // TODO: Rename and change types of parameters
    private ArrayList<ToCommentCourse> toCommentCourseArrayList;
    private Context context;
    private RecyclerView recyclerView;
    private OnPostCommentFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostCommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostCommentFragment newInstance(ArrayList<ToCommentCourse> toCommentCourseArrayList) {
        PostCommentFragment fragment = new PostCommentFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(COURSES, toCommentCourseArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    public PostCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            toCommentCourseArrayList = getArguments().getParcelableArrayList(COURSES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Toast.makeText(context,course.getId()+ " " + course.getTeacher(),Toast.LENGTH_SHORT).show();
        View postCommentView = inflater.inflate(R.layout.fragment_post_comment, container, false);

        ImageView backBtn = (ImageView) postCommentView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) postCommentView.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("待评价课程");

        ImageView backToMain = (ImageView) postCommentView.findViewById(R.id.backToMainScreen);
        backToMain.setBackgroundResource(R.mipmap.ic_home);
        backToMain.setOnClickListener(this);


        recyclerView = (RecyclerView) postCommentView.findViewById(R.id.post_comment_list);
        ToCommentAdapter toCommentAdapter = new ToCommentAdapter(context, toCommentCourseArrayList);
        recyclerView.setAdapter(toCommentAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        return postCommentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onToolbarInteraction(viewId);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            mListener = (OnPostCommentFragmentInteractionListener) activity;
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
    public interface OnPostCommentFragmentInteractionListener {
        // TODO: Update argument type and name
        void onToolbarInteraction(int viewId);
    }


}
