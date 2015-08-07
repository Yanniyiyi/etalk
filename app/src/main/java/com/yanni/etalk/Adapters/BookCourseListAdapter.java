package com.yanni.etalk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanni.etalk.Entities.BookedCourse;
import com.yanni.etalk.R;

import java.util.ArrayList;

/**
 * Created by macbookretina on 8/07/15.
 */
public class BookCourseListAdapter extends RecyclerView.Adapter<BookedCourseViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<BookedCourse> arrayList;
    private ShowBookedCourseDetailListener showBookedCourseDetailListener;


    public interface ShowBookedCourseDetailListener {
        void ShowBookedCourseDetail(BookedCourse bookedCourse);
    }


    public BookCourseListAdapter(Context context, ArrayList<BookedCourse> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        try {
            this.showBookedCourseDetailListener = ((ShowBookedCourseDetailListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public BookedCourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_my_book, viewGroup, false);
        BookedCourseViewHolder bookedCourseViewHolder = new BookedCourseViewHolder(view);
        return bookedCourseViewHolder;
    }

    @Override
    public void onBindViewHolder(BookedCourseViewHolder bookedCourseViewHolder, final int i) {
        final BookedCourse bookedCourse = arrayList.get(i);
        bookedCourseViewHolder.bookedCourseItem.setText(bookedCourse.getBookTime());
        bookedCourseViewHolder.bookedCourseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookedCourseDetailListener.ShowBookedCourseDetail(bookedCourse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class BookedCourseViewHolder extends RecyclerView.ViewHolder {
    TextView bookedCourseItem;

    public BookedCourseViewHolder(View itemView) {
        super(itemView);
        bookedCourseItem = (TextView) itemView.findViewById(R.id.my_book_item);
    }
}
