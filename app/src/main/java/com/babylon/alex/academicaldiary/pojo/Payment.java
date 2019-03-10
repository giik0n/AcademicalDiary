package com.babylon.alex.academicaldiary.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Payment implements Parcelable{

    private int id;
    private String studentID, cash, date;

    public Payment(int id, String studentID, String cash, String date) {
        this.id = id;
        this.studentID = studentID;
        this.cash = cash;
        this.date = date;
    }

    protected Payment(Parcel in) {
        id = in.readInt();
        studentID = in.readString();
        cash = in.readString();
        date = in.readString();
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getCash() {
        return cash;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(studentID);
        parcel.writeString(cash);
        parcel.writeString(date);
    }
}
