package com.yanni.etalk.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by macbookretina on 8/07/15.
 */
public class BookedCourse implements Parcelable {
    private String courseId;
    private String courseTitle;
    private String bookDate;
    private String bookTime;
    private String teacher;
    private String timestamp;

    public BookedCourse(String courseId, String courseTitle, String bookDate, String bookTime, String teacher,
                        String timestamp) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.bookDate = bookDate;
        this.bookTime = bookTime;
        this.teacher = teacher;
        this.timestamp = timestamp;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public BookedCourse(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);
        this.courseId = data[0];
        this.courseTitle = data[1];
        this.bookDate = data[2];
        this.bookTime = data[3];
        this.teacher = data[4];
        this.timestamp = data[5];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{this.courseId, this.courseTitle, this.bookDate, this.bookTime,
                this.teacher, this.timestamp});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public BookedCourse createFromParcel(Parcel in) {
            return new BookedCourse(in);
        }

        public BookedCourse[] newArray(int size) {
            return new BookedCourse[size];
        }
    };

}
