package com.yanni.etalk.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.yanni.etalk.Entities.DailyRecord;
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
public class DailyRecordListAdapter extends RecyclerView.Adapter<DailyRecordListAdapter.BaseViewHolder> {

    private ArrayList<DailyRecord> arrayList; //用户列表
    private Context context;
    private ShowCommentListener showCommentListener;


    public DailyRecordListAdapter(Context context, ArrayList<DailyRecord> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        try {
            this.showCommentListener = ((ShowCommentListener) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }

    public interface ShowCommentListener {
        void showStudentComment(DailyRecord dailyRecord);

        void showTeacherComment(DailyRecord dailyRecord);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.daily_record_item, viewGroup, false);

        return new BaseViewHolder(itemView, new IMyViewHolderClicks() {

            @Override
            public void onStudentCommentClick(final DailyRecord dailyRecord, final int position) {
                final Dialog dialog = new Dialog(context);
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
                            dailyRecord.setStudentComment(commentContent.getText().toString());
                            dailyRecord.setStudentRank(ratingBar.getRating() + "");
                            submitComment(dailyRecord.getCourseId(), commentContent.getText().toString(), ratingBar.getRating(),
                                    position);
                            Toast.makeText(context, commentContent.getText().toString() + " " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });
                dialog.show();

                // notifyItemChanged(position);
            }

            @Override
            public void onShowStudentCommentClick(DailyRecord dailyRecord) {
                showCommentListener.showStudentComment(dailyRecord);
            }

            @Override
            public void onShowTeacherCommentClick(DailyRecord dailyRecord) {
                showCommentListener.showTeacherComment(dailyRecord);
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
                        Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                        notifyItemChanged(position);
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

        DailyRecord dailyRecord = arrayList.get(position);
        baseViewHolder.bind(dailyRecord);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView courseTitle;
        TextView courseTime;
        TextView teacher;
        Button studentComment;
        Button teacherComment;
        IMyViewHolderClicks listener;

        public BaseViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);
            this.courseTitle = (TextView) itemView.findViewById(R.id.record_course_title);
            this.courseTime = (TextView) itemView.findViewById(R.id.record_course_time);
            this.teacher = (TextView) itemView.findViewById(R.id.record_teacher);
            this.studentComment = (Button) itemView.findViewById(R.id.record_student_comment);
            this.teacherComment = (Button) itemView.findViewById(R.id.record_teacher_comment);
            this.listener = listener;
            studentComment.setOnClickListener(this);
            teacherComment.setOnClickListener(this);
        }

        public void bind(DailyRecord dailyRecord) {
            courseTitle.setText("学习课程: " + dailyRecord.getCourseTitle());
            teacher.setText("上课老师: " + dailyRecord.getTeacher());
            courseTime.setText("上课时间: " + dailyRecord.getDetailTime());
            if (dailyRecord.getStudentRank().equals("0")) {
                studentComment.setText("我要评价");
                studentComment.setBackgroundResource(R.drawable.i_want_to_comment_button_style);
            } else {
                studentComment.setText("我的评价");
                studentComment.setBackgroundResource(R.drawable.my_comment_button_style);
            }

            if (dailyRecord.getTeacherRank().equals("0")) {
                teacherComment.setText("暂无老师评价");
                teacherComment.setClickable(false);
                teacherComment.setBackgroundResource(R.drawable.no_teacher_comment_button_style);
            } else {
                teacherComment.setText("查看老师评价");
                teacherComment.setClickable(true);
                teacherComment.setBackgroundResource(R.drawable.check_teacher_comment_button_style);
            }

            studentComment.setTag(dailyRecord);
            teacherComment.setTag(dailyRecord);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.record_student_comment:
                    DailyRecord student = (DailyRecord) studentComment.getTag();
                    if (student.getStudentRank().equals("0")) {
                        listener.onStudentCommentClick(student, getLayoutPosition());
                    } else {
                        listener.onShowStudentCommentClick(student);
                    }
                    break;
                case R.id.record_teacher_comment:
                    DailyRecord teacher = (DailyRecord) teacherComment.getTag();
                    if (!teacher.getTeacherComment().isEmpty()) {
                        listener.onShowTeacherCommentClick(teacher);
                    }
                    // mListener.onItemClick(((User)tvFollowStatus.getTag()).getId());
                    break;
            }
        }
    }

    private interface IMyViewHolderClicks {
        void onStudentCommentClick(DailyRecord dailyRecord, int position);

        void onShowStudentCommentClick(DailyRecord dailyRecord);

        void onShowTeacherCommentClick(DailyRecord dailyRecord);
    }
}
