package com.yanni.etalk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanni.etalk.Entities.ToCommentCourse;
import com.yanni.etalk.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by macbookretina on 4/07/15.
 */
public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder> {


    public interface ShowToCommentCourseListener {
        void showToCommentCourses(ArrayList<ToCommentCourse> toCommentCourseArrayList);
    }


    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<String> arrayList;
    private ShowToCommentCourseListener listener;
    private LinkedHashMap<String, ArrayList<ToCommentCourse>> map;

    public SimpleAdapter(Context context, LinkedHashMap<String, ArrayList<ToCommentCourse>> map) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.map = map;
        this.arrayList = new ArrayList<>(map.keySet());
        try {
            this.listener = ((ShowToCommentCourseListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_course, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.courseItem.setText(arrayList.get(i));

        viewHolder.courseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    // listener.showToCommentCourses(map.get(((TextView)view).getText()));
                    TextView textView = (TextView) view;
                    listener.showToCommentCourses(map.get(textView.getText()));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView courseItem;

    public MyViewHolder(View itemView) {
        super(itemView);
        courseItem = (TextView) itemView.findViewById(R.id.course_item);
    }
}
