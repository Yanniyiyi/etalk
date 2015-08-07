package com.yanni.etalk.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macbookretina on 5/07/15.
 */
public class MonthlyRecord implements Parcelable {

    private String courseId;
    private String courseTime;
    private String courseTitle;
    private String teacher;
    private String detailTime;
    private String teacherComment;
    private String teacherRank;
    private String studentComment;
    private String studentRank;

    public MonthlyRecord(String courseId, String courseTime) {
        this.courseId = courseId;
        this.courseTime = courseTime;
    }


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public MonthlyRecord(Parcel in) {
        String[] data = new String[9];
        in.readStringArray(data);
        this.courseId = data[0];
        this.courseTime = data[1];
        this.courseTitle = data[2];
        this.teacher = data[3];
        this.detailTime = data[4];
        this.teacherComment = data[5];
        this.teacherRank = data[6];
        this.studentComment = data[7];
        this.studentRank = data[8];

    }


    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDetailTime() {
        return detailTime;
    }

    public void setDetailTime(String detailTime) {
        this.detailTime = detailTime;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public String getTeacherRank() {
        return teacherRank;
    }

    public void setTeacherRank(String teacherRank) {
        this.teacherRank = teacherRank;
    }

    public String getStudentComment() {
        return studentComment;
    }

    public void setStudentComment(String studentComment) {
        this.studentComment = studentComment;
    }

    public String getStudentRank() {
        return studentRank;
    }

    public void setStudentRank(String studentRank) {
        this.studentRank = studentRank;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.courseId, this.courseTime, this.courseTitle, this.teacher,
                this.detailTime, this.teacherComment, this.teacherRank, this.studentComment, this.studentRank});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MonthlyRecord createFromParcel(Parcel in) {
            return new MonthlyRecord(in);
        }

        public MonthlyRecord[] newArray(int size) {
            return new MonthlyRecord[size];
        }
    };

}
