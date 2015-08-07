package com.yanni.etalk.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Entities.EtalkClass;
import com.yanni.etalk.R;
import com.yanni.etalk.Utilities.DateUtility;
import com.yanni.etalk.Utilities.ErrorHelper;
import com.yanni.etalk.Utilities.EtalkSharedPreference;
import com.yanni.etalk.Utilities.JsonObjectGenerator;
import com.yanni.etalk.Utilities.UrlManager;
import com.yanni.etalk.Utilities.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by macbookretina on 13/07/15.
 */
public class BookClassTimeListAdapter extends RecyclerView.Adapter<BookClassTimeListViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<EtalkClass> etalkClassArrayList;
    private String date;


    public BookClassTimeListAdapter(Context context, ArrayList<EtalkClass> arrayList, String date) {
        this.etalkClassArrayList = arrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.date = date;
    }

    @Override
    public BookClassTimeListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_book_class_time, viewGroup, false);
        return new BookClassTimeListViewHolder(view, new BookClassItemClick() {
            @Override
            public void showBookClassDialog(EtalkClass etalkClass, int position) {
                bookClass(position, etalkClass);
            }
        });
    }

    @Override
    public void onBindViewHolder(BookClassTimeListViewHolder bookClassTimeListViewHolder, final int i) {
        EtalkClass etalkClass = etalkClassArrayList.get(i);
        bookClassTimeListViewHolder.bind(etalkClass);

    }

    private void bookClass(final int itemPosition, final EtalkClass etalkClass) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_book_class);

        Button confirm = (Button) dialog.findViewById(R.id.confirm_book_class);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_book_class);
        TextView textView = (TextView) dialog.findViewById(R.id.book_class_info);
        textView.setText("您预约了" + DateUtility.bookClassDialogDate(date) + "\n" +
                DateUtility.getTimePeriod(etalkClass.getPeriod()) + "的一节课");
        textView.setTextColor(Color.BLACK);

        confirm.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           confirmBook(etalkClass, itemPosition);
                                           dialog.cancel();
                                       }
                                   }
        );
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void confirmBook(EtalkClass etalkClass, final int itemPosition) {
        final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
        JSONObject obj =
                JsonObjectGenerator.createBookClassJsonObject(EtalkSharedPreference.getPrefUserId(context),
                        etalkClass.getPackageId(), etalkClass.getTime(), etalkClass.getLm()
                );
        final RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                UrlManager.BOOK_CLASS_URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    System.out.println(response.toString());
                    if (response.getString("status").equals("0")) {
                        // progressbar gone
                        System.out.println(response.getJSONArray("data").length());
                        ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                    } else {
                        etalkClassArrayList.get(itemPosition).setStatus("2");
                        notifyItemChanged(itemPosition);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", EtalkSharedPreference.getPrefToken(context));
                headers.put("Content-type", "application/json;charset=UTF-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public int getItemCount() {
        return etalkClassArrayList.size();
    }


    public void updateList(ArrayList<EtalkClass> etalkClassArrayList, String date) {
        System.out.println("update invoked");
        this.etalkClassArrayList = etalkClassArrayList;
        this.date = date;
        notifyDataSetChanged();
    }
}

class BookClassTimeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView courseItem;
    BookClassItemClick listener;
    private final String NOT_AVAILABLE = "0";
    private final String AVAILABLE = "1";
    private final String BOOKED = "2";

    public BookClassTimeListViewHolder(View itemView, BookClassItemClick listener) {
        super(itemView);
        courseItem = (TextView) itemView.findViewById(R.id.book_class_time);
        this.listener = listener;
    }

    public void bind(EtalkClass etalkClass) {
        courseItem.setText(etalkClass.getPeriod());
        switch (etalkClass.getStatus()) {
            case NOT_AVAILABLE:
                courseItem.setBackgroundColor(Color.parseColor("#cccccc"));
                courseItem.setClickable(false);
                break;
            case AVAILABLE:
                courseItem.setBackgroundColor(Color.parseColor("#CFEA91"));
                courseItem.setOnClickListener(this);
                break;
            case BOOKED:
                courseItem.setBackgroundColor(Color.parseColor("#F77D7D"));
                courseItem.setClickable(false);
                break;
            default:
                break;
        }
        courseItem.setTag(etalkClass);
    }

    @Override
    public void onClick(View view) {

        listener.showBookClassDialog((EtalkClass) courseItem.getTag(), getLayoutPosition());
    }
}

interface BookClassItemClick {
    void showBookClassDialog(EtalkClass etalkClass, int position);
}
