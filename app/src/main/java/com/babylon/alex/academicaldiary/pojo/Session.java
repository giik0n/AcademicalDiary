package com.babylon.alex.academicaldiary.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Session implements Parcelable{
    private int id;
    private String lesson,type, date, time, classroom;

    public Session(int id, String lesson, String type, String date, String time, String classroom) {
        this.id = id;
        this.lesson = lesson;
        this.type = type;
        this.date = date;
        this.time = time;
        this.classroom = classroom;
    }

    protected Session(Parcel in) {
        id = in.readInt();
        lesson = in.readString();
        type = in.readString();
        date = in.readString();
        time = in.readString();
        classroom = in.readString();
    }

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getLesson() {
        return lesson;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
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
        parcel.writeString(type);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(classroom);
    }
}
