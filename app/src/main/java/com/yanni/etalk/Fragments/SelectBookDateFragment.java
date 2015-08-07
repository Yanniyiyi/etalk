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

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.DateUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectBookDateFragment.OnSelectBookDateInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectBookDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectBookDateFragment extends Fragment implements OnDateChangedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DATE = "date";
    private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    // TODO: Rename and change types of parameters
    private String strDate;
    private Context context;
    private OnSelectBookDateInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectBookDateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectBookDateFragment newInstance(String date) {
        SelectBookDateFragment fragment = new SelectBookDateFragment();
        Bundle args = new Bundle();
        args.putString(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    public SelectBookDateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            strDate = getArguments().getString(DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View selectBookDateView = inflater.inflate(R.layout.fragment_select_book_date, container, false);
        MaterialCalendarView we = (MaterialCalendarView) selectBookDateView.findViewById(R.id.calendarView);
        we.setMinimumDate(new Date());

        we.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat(
                "yyyy MMMM", Locale.getDefault()
        )));
        //we.setShowOtherDates(true);
        we.setSelectedDate(DateUtility.strToDate(strDate));
        we.setOnDateChangedListener(this);
        we.setHeaderTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        we.setWeekDayTextAppearance(R.style.TextAppearance_AppCompat_Medium);
        we.setDateTextAppearance(R.style.calendarTextTheme);

        ImageView backBtn = (ImageView) selectBookDateView.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        TextView toolBarTitle = (TextView) selectBookDateView.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("预约课程");

        ImageView backToMain = (ImageView) selectBookDateView.findViewById(R.id.backToMainScreen);
        backToMain.setBackgroundResource(R.mipmap.ic_home);
        backToMain.setOnClickListener(this);
        return selectBookDateView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId) {
        if (mListener != null) {
            mListener.onSelectBookDateInteraction(viewId, strDate);
            mListener.onDateChanged(strDate);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        try {
            mListener = (OnSelectBookDateInteractionListener) activity;
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
    public void onDateChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {

        strDate = FORMATTER.format(calendarDay.getDate());
        mListener.onDateChanged(strDate);
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
    public interface OnSelectBookDateInteractionListener {
        // TODO: Update argument type and name
        void onSelectBookDateInteraction(int viewId, String strDate);

        void onDateChanged(String strDate);
    }

}
