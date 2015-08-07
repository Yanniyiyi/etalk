package com.yanni.etalk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanni.etalk.Entities.MonthlyRecord;
import com.yanni.etalk.R;

import java.util.ArrayList;

/**
 * Created by macbookretina on 6/07/15.
 */
public class MonthlyRecordListAdapter extends RecyclerView.Adapter<MonthlyRecordViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<MonthlyRecord> arrayList;


    public MonthlyRecordListAdapter(Context context, ArrayList<MonthlyRecord> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MonthlyRecordViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.monthly_record_item, viewGroup, false);
        MonthlyRecordViewHolder monthlyRecordViewHolder = new MonthlyRecordViewHolder(view);
        return monthlyRecordViewHolder;
    }

    @Override
    public void onBindViewHolder(MonthlyRecordViewHolder monthlyRecordViewHolder, int i) {
        monthlyRecordViewHolder.courseItem.setText(arrayList.get(i).getCourseTime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class MonthlyRecordViewHolder extends RecyclerView.ViewHolder {
    TextView courseItem;

    public MonthlyRecordViewHolder(View itemView) {
        super(itemView);
        courseItem = (TextView) itemView.findViewById(R.id.record_item);
    }
}
