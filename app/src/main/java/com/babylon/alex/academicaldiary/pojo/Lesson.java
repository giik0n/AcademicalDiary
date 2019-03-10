package com.babylon.alex.academicaldiary.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Lesson implements Parcelable {
    private int id;
    private String name, teacher, hours, type, semester;

    public Lesson(int id, String name, String teacher, String hours, String type, String semester) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.hours = hours;
        this.type = type;
        this.semester = semester;
    }

    protected Lesson(Parcel in) {
        id = in.readInt();
        name = in.readString();
        teacher = in.readString();
        hours = in.readString();
        type = in.readString();
        semester = in.readString();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getHours() {
        return hours;
    }

    public String getType() {
        return type;
    }

    public String getSemester() {
        return semester;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(teacher);
        parcel.writeString(hours);
        parcel.writeString(type);
        parcel.writeString(semester);
    }
}
