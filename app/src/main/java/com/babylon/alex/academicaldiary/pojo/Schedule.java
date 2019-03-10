package com.babylon.alex.academicaldiary.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable {
    private int id;
    private String lesson, day, position, classroom;

    public Schedule(int id, String lesson, String day, String position, String classroom) {
        this.id = id;
        this.lesson = lesson;
        this.day = day;
        this.position = position;
        this.classroom = classroom;
    }

    protected Schedule(Parcel in) {
        id = in.readInt();
        lesson = in.readString();
        day = in.readString();
        position = in.readString();
        classroom = in.readString();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getLesson() {
        return lesson;
    }

    public String getDay() {
        return day;
    }

    public String getPosition() {
        return position;
    }

    public String getClassroom() {
        return classroom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(lesson);
        parcel.writeString(day);
        parcel.writeString(position);
        parcel.writeString(classroom);
    }
}
