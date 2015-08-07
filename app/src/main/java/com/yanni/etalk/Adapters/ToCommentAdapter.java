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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yanni.etalk.Entities.ToCommentCourse;
import com.yanni.etalk.R;
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
 * Created by macbookretina on 6/07/15.
 */
public class ToCommentAdapter extends RecyclerView.Adapter<ToCommentAdapter.BaseViewHolder> {

    private ArrayList<ToCommentCourse> arrayList; //用户列表
    private Context context;


    public ToCommentAdapter(Context context, ArrayList<ToCommentCourse> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.post_comment_item, viewGroup, false);

        return new BaseViewHolder(itemView, new IMyViewHolderClicks() {

            @Override
            public void onCommentClick(final ToCommentCourse toCommentCourse, final int position) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_input_comment);

                // set the custom dialog components - text, image and button
                final EditText commentContent = (EditText) dialog.findViewById(R.id.comment_input);
                final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);


                Button dialogButton = (Button) dialog.findViewById(R.id.submit_comment);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (commentContent.getText().toString().isEmpty()) {
                            Toast.makeText(context, "请输入对老师的评价", Toast.LENGTH_SHORT).show();
                        } else if (ratingBar.getRating() == 0.0) {
                            Toast.makeText(context, "请为老师打分", Toast.LENGTH_SHORT).show();

                        } else {
                            submitComment(toCommentCourse.getId(), commentContent.getText().toString(), ratingBar.getRating(),
                                    position);
                            Toast.makeText(context, commentContent.getText().toString() + " " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });
                dialog.show();

                // notifyItemChanged(position);
            }

        });
    }


    private void submitComment(String courseId, String commentContent, float score, final int position) {


        final ACProgressFlower progressDialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();


        JSONObject obj = JsonObjectGenerator.createCommentJsonObject(
                EtalkSharedPreference.getPrefUserId(context), courseId, commentContent, score);
        JsonObjectRequest submitComment = new JsonObjectRequest(Request.Method.POST,
                UrlManager.SUBMIT_COMMENT, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    System.out.println(response.toString());

                    if (response.getString("status").equals("0")) {
                        // progressbar gone
                        System.out.println(response.toString());
                        ErrorHelper.displayErrorInfo(context, response.getString("error_code"));
                    } else {
                        arrayList.remove(position);
                        Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(position);
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
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        requestQueue.add(submitComment);
    }


    @Override
    public void onBindViewHolder(final BaseViewHolder baseViewHolder, final int position) {
        baseViewHolder.bind(arrayList.get(position));
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView courseTime;
        TextView teacher;
        Button showCommentDialog;
        IMyViewHolderClicks listener;

        public BaseViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);

            this.courseTime = (TextView) itemView.findViewById(R.id.course_time);
            this.teacher = (TextView) itemView.findViewById(R.id.teacher_name);
            this.listener = listener;
            this.showCommentDialog = (Button) itemView.findViewById(R.id.showCommentDialog);
            this.showCommentDialog.setOnClickListener(this);

        }

        public void bind(ToCommentCourse toCommentCourse) {
            teacher.setText("上课老师: " + toCommentCourse.getTeacher());
            courseTime.setText("上课时间: " + toCommentCourse.getDate() + " " + toCommentCourse.getTime());

            showCommentDialog.setTag(toCommentCourse);

        }

        @Override
        public void onClick(View v) {
            listener.onCommentClick((ToCommentCourse) showCommentDialog.getTag(), getLayoutPosition());
        }
    }

    private interface IMyViewHolderClicks {
        void onCommentClick(ToCommentCourse toCommentCourse, int position);
    }
}




