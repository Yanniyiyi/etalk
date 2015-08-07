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

/**
 * Created by macbookretina on 7/07/15.
 */
public class PackageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<EtalkPackage> arrayList;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final String PAID = "已付款";
    private static final String CANCELED = "已取消";
    private static final String NOT_PAY = "未付款";


    public PackageListAdapter(Context context, ArrayList<EtalkPackage> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_ITEM) {
            View view = layoutInflater.inflate(R.layout.item_package, viewGroup, false);
            PackageListViewHolder packageListViewHolder = new PackageListViewHolder(view);
            return packageListViewHolder;
        } else if (i == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
            View view = layoutInflater.inflate(R.layout.list_header, viewGroup, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
            return headerViewHolder;
        }
//        View view = layoutInflater.inflate(R.layout.item_package,viewGroup,false);
//        PackageListViewHolder packageListViewHolder = new PackageListViewHolder(view);
//        return packageListViewHolder;
        throw new RuntimeException("there is no type that matches the type " + i + " + make sure your using types correctly");
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PackageListViewHolder) {
            PackageListViewHolder packageListViewHolder = (PackageListViewHolder) viewHolder;
            EtalkPackage etalkPackage = getItem(i);
            packageListViewHolder.packageName.setText(etalkPackage.getPackageName());
            packageListViewHolder.packagePurchaseTime.setText(etalkPackage.getPurchaseTime());
            packageListViewHolder.packageValidTime.setText(etalkPackage.getValidDate());
            packageListViewHolder.packageTotalHours.setText(etalkPackage.getTotalHours());
            packageListViewHolder.packageLastHours.setText(etalkPackage.getLastHours());
            packageListViewHolder.packagePrice.setText(etalkPackage.getPrice());
            switch (etalkPackage.getStatus()) {
                case "0":
                    packageListViewHolder.packageStatus.setText(NOT_PAY);
                    break;
                case "1":
                    packageListViewHolder.packageStatus.setText(PAID);
                    break;
                case "2":
                    packageListViewHolder.packageStatus.setText(CANCELED);
                    break;
                default:
                    break;
            }
            //cast holder to VHItem and set data
        } else if (viewHolder instanceof HeaderViewHolder) {
            if (arrayList.size() > 0) {
                ((HeaderViewHolder) viewHolder).header.setText("套餐详情");
                ((HeaderViewHolder) viewHolder).header.setTextColor(Color.parseColor("#0000FF"));
            }
            //cast holder to VHHeader and set data for header.
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private EtalkPackage getItem(int position) {
        return arrayList.get(position - 1);
    }

}

class PackageListViewHolder extends RecyclerView.ViewHolder {
    TextView packageName;
    TextView packagePurchaseTime;
    TextView packageValidTime;
    TextView packageTotalHours;
    TextView packageLastHours;
    TextView packagePrice;
    TextView packageStatus;

    public PackageListViewHolder(View itemView) {
        super(itemView);
        packageName = (TextView) itemView.findViewById(R.id.package_name);
        packagePurchaseTime = (TextView) itemView.findViewById(R.id.package_purchase_time);
        packageValidTime = (TextView) itemView.findViewById(R.id.package_valid_time);
        packageTotalHours = (TextView) itemView.findViewById(R.id.package_total_hours);
        packageLastHours = (TextView) itemView.findViewById(R.id.package_last_hours);
        packagePrice = (TextView) itemView.findViewById(R.id.package_price);
        packageStatus = (TextView) itemView.findViewById(R.id.package_status);

    }
}

class HeaderViewHolder extends RecyclerView.ViewHolder {
    TextView header;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        header = (TextView) itemView.findViewById(R.id.list_header);

    }
}

