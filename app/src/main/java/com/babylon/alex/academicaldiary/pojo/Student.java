package com.babylon.alex.academicaldiary.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    private int id;
    private String name, birthday, homeAdress, currentAdress, phone, course,
            faculty, email, privilege, parentsName, parentsAdress, aboutFamily,
            avgRating, studyForm;

    public Student(int id, String name, String birthday, String homeAdress, String currentAdress, String phone, String course, String faculty, String email, String privilege, String parentsName, String parentsAdress, String aboutFamily, String avgRating, String studyForm) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.homeAdress = homeAdress;
        this.currentAdress = currentAdress;
        this.phone = phone;
        this.course = course;
        this.faculty = faculty;
        this.email = email;
        this.privilege = privilege;
        this.parentsName = parentsName;
        this.parentsAdress = parentsAdress;
        this.aboutFamily = aboutFamily;
        this.avgRating = avgRating;
        this.studyForm = studyForm;
    }

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
        birthday = in.readString();
        homeAdress = in.readString();
        currentAdress = in.readString();
        phone = in.readString();
        course = in.readString();
        faculty = in.readString();
        email = in.readString();
        privilege = in.readString();
        parentsName = in.readString();
        parentsAdress = in.readString();
        aboutFamily = in.readString();
        avgRating = in.readString();
        studyForm = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHomeAdress() {
        return homeAdress;
    }

    public String getCurrentAdress() {
        return currentAdress;
    }

    public String getPhone() {
        return phone;
    }

    public String getCourse() {
        return course;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getEmail() {
        return email;
    }

    public String getPrivilege() {
        return privilege;
    }

    public String getParentsName() {
        return parentsName;
    }

    public String getParentsAdress() {
        return parentsAdress;
    }

    public String getAboutFamily() {
        return aboutFamily;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public String getStudyForm() {
        return studyForm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(birthday);
        parcel.writeString(homeAdress);
        parcel.writeString(currentAdress);
        parcel.writeString(phone);
        parcel.writeString(course);
        parcel.writeString(faculty);
        parcel.writeString(email);
        parcel.writeString(privilege);
        parcel.writeString(parentsName);
        parcel.writeString(parentsAdress);
        parcel.writeString(aboutFamily);
        parcel.writeString(avgRating);
        parcel.writeString(studyForm);
    }
}
