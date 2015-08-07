package com.yanni.etalk.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanni.etalk.Entities.EtalkPackage;
import com.yanni.etalk.R;

import java.util.ArrayList;


public class BookClassListAdapter extends RecyclerView.Adapter<BookClassViewHolder> {


    public interface ShowBookClassDetailListener {
        void showBookClassDetail(EtalkPackage etalkPackage);
    }


    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<EtalkPackage> arrayList;
    private ShowBookClassDetailListener listener;

    public BookClassListAdapter(Context context, ArrayList<EtalkPackage> arrayList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        try {
            this.listener = ((ShowBookClassDetailListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    @Override
    public BookClassViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_book_class_list, viewGroup, false);
        BookClassViewHolder myViewHolder = new BookClassViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(BookClassViewHolder viewHolder, int i) {
        final EtalkPackage etalkPackage = arrayList.get(i);
        viewHolder.bindView(etalkPackage);

        if (viewHolder.rootView.isClickable()) {
            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.showBookClassDetail(etalkPackage);
                }
            });
        } else {

        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class BookClassViewHolder extends RecyclerView.ViewHolder {
    TextView courseTitle;
    TextView purchaseStatus;
    View rootView;
    private static final String PAID = "";
    private static final String CANCELED = "已取消";
    private static final String NOT_PAY = "未付款";

    public BookClassViewHolder(View itemView) {
        super(itemView);
        rootView = itemView;
        courseTitle = (TextView) itemView.findViewById(R.id.book_class_title);
        purchaseStatus = (TextView) itemView.findViewById(R.id.purchase_status);


    }

    public void bindView(EtalkPackage etalkPackage) {
        this.courseTitle.setText(etalkPackage.getPackageName());
        switch (etalkPackage.getStatus()) {
            case "0":
                rootView.setClickable(false);
                this.purchaseStatus.setText(NOT_PAY);
                this.purchaseStatus.setTextColor(Color.RED);
                this.courseTitle.setTextColor(Color.GRAY);
                this.purchaseStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case "1":
                rootView.setClickable(true);
                this.purchaseStatus.setText(PAID);
                this.purchaseStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_forward, 0);
                this.courseTitle.setTextColor(Color.BLACK);
                break;
            case "2":
                rootView.setClickable(false);
                this.purchaseStatus.setText(CANCELED);
                this.purchaseStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            default:
                this.purchaseStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                this.purchaseStatus.setText("");
                break;
        }
    }

}
