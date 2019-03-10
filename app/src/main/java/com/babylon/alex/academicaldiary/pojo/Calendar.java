package com.babylon.alex.academicaldiary.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Calendar implements Parcelable{
    private int id;
    private String name, description, date, time;

    public Calendar(int id, String name, String description, String date, String time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    protected Calendar(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        date = in.readString();
        time = in.readString();
    }

    public static final Creator<Calendar> CREATOR = new Creator<Calendar>() {
        @Override
        public Calendar createFromParcel(Parcel in) {
            return new Calendar(in);
        }

        @Override
        public Calendar[] newArray(int size) {
            return new Calendar[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeString(time);
    }
}
