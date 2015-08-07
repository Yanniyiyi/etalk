package com.yanni.etalk.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macbookretina on 4/07/15.
 */
public class ToCommentCourse implements Parcelable {
    private String date;
    private String time;
    private String id;
    private String teacher;


    public ToCommentCourse(String date, String time, String id, String teacher) {
        this.date = date;
        this.time = time;
        this.id = id;
        this.teacher = teacher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public ToCommentCourse(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);
        this.date = data[0];
        this.time = data[1];
        this.id = data[2];
        this.teacher = data[3];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.date, this.time, this.id, this.teacher});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ToCommentCourse createFromParcel(Parcel in) {
            return new ToCommentCourse(in);
        }

        public ToCommentCourse[] newArray(int size) {
            return new ToCommentCourse[size];
        }
    };
}
