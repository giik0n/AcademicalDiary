package com.babylon.alex.academicaldiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.babylon.alex.academicaldiary.pojo.Calendar;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Payment;
import com.babylon.alex.academicaldiary.pojo.Schedule;
import com.babylon.alex.academicaldiary.pojo.Session;
import com.babylon.alex.academicaldiary.pojo.Student;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_LESSONS = "Lessons";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TEACHER = "teacher";
    private static final String KEY_HOURS = "hours";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SEMESTER = "semester";

    private static final String TABLE_STUDENTS = "Students";

    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_HOMEADDRESS = "homeAdress";
    private static final String KEY_CURRENTADDRESS = "currentAdress";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_COURSE = "course";
    private static final String KEY_FACULTY = "faculty";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PRIVILEGE = "privilege";
    private static final String KEY_PARENTSNAME = "parentsName" ;
    private static final String KEY_PARENTSADRESS = "parentsAdress";
    private static final String KEY_ABOUTFAMILY = "aboutFamily";
    private static final String KEY_AVGRATING = "avgRating";
    private static final String KEY_STUDYFORM = "studyForm";

    private static final String TABLE_SESSION = "Session";
    private static final String KEY_LESSON = "lessonId";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_CLASSROOM = "classroom";
    private static final String KEY_DESCRIPTION = "description";
    private static final String TABLE_CALENDAR = "Calendar";
    private static final String KEY_POSITION = "position";
    private static final String TABLE_SCHEDULE = "Schedule";
    private static final String KEY_STUDENT = "student";
    private static final String TABLE_PAYMENT = "Payment";
    private static final String KEY_CASH = "cash";


    Context context;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Diary";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String[] create_tables = {
                "CREATE TABLE "+TABLE_LESSONS+" ("+KEY_ID+
                        " INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_NAME+" TEXT," + KEY_TEACHER+" TEXT,"+
                        KEY_HOURS+" INTEGER,"+KEY_TYPE+" TEXT,"+KEY_SEMESTER+" INTEGER)",
                "CREATE TABLE "+TABLE_STUDENTS+" ("+KEY_ID+
                        " INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_NAME+" TEXT," + KEY_BIRTHDAY +
                        " TEXT,"+ KEY_HOMEADDRESS +" TEXT,"+ KEY_CURRENTADDRESS +" TEXT,"+KEY_PHONE+
                        " TEXT,"+KEY_COURSE+" INTEGER,"+ KEY_FACULTY +" TEXT," + KEY_EMAIL+" TEXT,"+KEY_PRIVILEGE+
                        " TEXT,"+KEY_PARENTSNAME+" TEXT,"+KEY_PARENTSADRESS+" TEXT,"+KEY_ABOUTFAMILY+" TEXT,"+KEY_AVGRATING+
                        " TEXT,"+KEY_STUDYFORM+" TEXT)",
                "CREATE TABLE "+TABLE_SESSION+" ("+KEY_ID+
                        " INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_LESSON+" TEXT," + KEY_TYPE+
                        " TEXT,"+ KEY_DATE+" TEXT,"+KEY_TIME+" TEXT,"+ KEY_CLASSROOM +" TEXT)",
                "CREATE TABLE "+TABLE_SCHEDULE+" ("+KEY_ID+
                        " INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_LESSON+" TEXT,"+KEY_DATE+" TEXT, "
                        +KEY_POSITION+" INTEGER, "+KEY_CLASSROOM +" TEXT)",
                "CREATE TABLE "+TABLE_CALENDAR+" ("+KEY_ID+
                        " INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_NAME+" TEXT," + KEY_DESCRIPTION+
                        " TEXT,"+ KEY_DATE+" TEXT,"+KEY_TIME+" TEXT)",
                "CREATE TABLE "+TABLE_PAYMENT+" ("+KEY_ID+
                        " INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_STUDENT+" TEXT," + KEY_CASH+
                        " TEXT,"+ KEY_DATE+" TEXT)"
        };

        for (int i =0;i<create_tables.length;i++){
            sqLiteDatabase.execSQL(create_tables[i]);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Student> getStudents(){
        List<Student> students = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            do{
                Student student = new Student(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getString(14));
                students.add(student);
            }while(cursor.moveToNext());
        }
        return students;
    }

    public  void addNewStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(KEY_ID);
        values.put(KEY_NAME,student.getName());
        values.put(KEY_BIRTHDAY,student.getBirthday());
        values.put(KEY_HOMEADDRESS,student.getHomeAdress());
        values.put(KEY_CURRENTADDRESS,student.getCurrentAdress());
        values.put(KEY_PHONE,student.getPhone());
        values.put(KEY_COURSE,student.getCourse());
        values.put(KEY_FACULTY,student.getFaculty());
        values.put(KEY_EMAIL,student.getEmail());
        values.put(KEY_PRIVILEGE,student.getPrivilege());
        values.put(KEY_PARENTSNAME,student.getParentsName());
        values.put(KEY_PARENTSADRESS,student.getParentsAdress());
        values.put(KEY_ABOUTFAMILY,student.getAboutFamily());
        values.put(KEY_AVGRATING,student.getAvgRating());
        values.put(KEY_STUDYFORM,student.getStudyForm());
        db.insert(TABLE_STUDENTS,null,values);
        db.close();
    }

    public void deleteStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS,KEY_ID+" =?",new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public List<Lesson> getLessons() {
        List<Lesson> lessons = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + TABLE_LESSONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            do{
                Lesson lesson = new Lesson(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                lessons.add(lesson);
            }while(cursor.moveToNext());
        }
        return lessons;
    }


    public void deleteLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LESSONS,KEY_ID+" =?",new String[]{String.valueOf(lesson.getId())});
        db.close();
    }

    public  void addNewLesson(Lesson student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(KEY_ID);
        values.put(KEY_NAME,student.getName());
        values.put(KEY_TEACHER,student.getTeacher());
        values.put(KEY_HOURS,student.getHours());
        values.put(KEY_TYPE,student.getType());
        values.put(KEY_SEMESTER,student.getSemester());
        db.insert(TABLE_LESSONS,null,values);
        db.close();
    }

    public void addNewSession(Session session) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(KEY_ID);
        values.put(KEY_LESSON,session.getLesson());
        values.put(KEY_TYPE,session.getType());
        values.put(KEY_DATE,session.getDate());
        values.put(KEY_TIME,session.getTime());
        values.put(KEY_CLASSROOM,session.getClassroom());
        db.insert(TABLE_SESSION,null,values);
        db.close();
    }

    public List<Session> getSession() {
        List<Session> session = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + TABLE_SESSION;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            do{
                Session sessionItem = new Session(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                session.add(sessionItem);
            }while(cursor.moveToNext());
        }
        return session;
    }

    public void deleteSession(Session session) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SESSION,KEY_ID+" =?",new String[]{String.valueOf(session.getId())});
        db.close();
    }

    public String getLessonNameById(String id){
        String lessonName = "";
        String sqlQuery = "SELECT * FROM " + TABLE_LESSONS + " WHERE "+KEY_ID+" = '"+id+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            lessonName = cursor.getString(1);
        }
        return lessonName;
    }

    public List<Payment> getPayment() {
        List<Payment> payment = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + TABLE_PAYMENT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            do{
                Payment paymentItem = new Payment(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                payment.add(paymentItem);
            }while(cursor.moveToNext());
        }
        return payment;
    }

    public void addNewPayment(Payment payment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(KEY_ID);
        values.put(KEY_STUDENT,payment.getStudentID());
        values.put(KEY_CASH,payment.getCash());
        values.put(KEY_DATE,payment.getDate());
        db.insert(TABLE_PAYMENT,null,values);
        db.close();
    }

    public void deletePayment(Payment payment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PAYMENT,KEY_ID+" =?",new String[]{String.valueOf(payment.getId())});
        db.close();
    }

    public List<Calendar> getCalendar() {
        List<Calendar> calendar = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + TABLE_CALENDAR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            do{
                Calendar calendarItem = new Calendar(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                calendar.add(calendarItem);
            }while(cursor.moveToNext());
        }
        return calendar;
    }

    public void addNewCalendar(Calendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(KEY_ID);
        values.put(KEY_NAME,calendar.getName());
        values.put(KEY_DESCRIPTION,calendar.getDescription());
        values.put(KEY_DATE,calendar.getDate());
        values.put(KEY_TIME,calendar.getTime());
        db.insert(TABLE_CALENDAR,null,values);
        db.close();
    }

    public void deleteCalendar(Calendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALENDAR,KEY_ID+" =?",new String[]{String.valueOf(calendar.getId())});
        db.close();
    }

    public List<Schedule> getSchedule() {
        List<Schedule> schedule = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + TABLE_SCHEDULE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            do{
                Schedule scheduleItem = new Schedule(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                schedule.add(scheduleItem);
            }while(cursor.moveToNext());
        }
        return schedule;
    }

    public void addNewSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(KEY_ID);
        values.put(KEY_LESSON,schedule.getLesson());
        values.put(KEY_DATE,schedule.getDay());
        values.put(KEY_POSITION,schedule.getPosition());
        values.put(KEY_CLASSROOM,schedule.getClassroom());
        db.insert(TABLE_SCHEDULE,null,values);
        db.close();
    }

    public void deleteSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCHEDULE,KEY_ID+" =?",new String[]{String.valueOf(schedule.getId())});
        db.close();
    }
    public List<Schedule> getScheduleByDay(String day){
        List<Schedule> schedules = new ArrayList<>();
        String sqlQuery = "SELECT * FROM " + TABLE_SCHEDULE + " WHERE "+KEY_DATE +" = \""+ day+"\" ORDER BY position ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        if(cursor.moveToFirst()){
            do{
                Schedule schedule = new Schedule(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                schedules.add(schedule);
            }while(cursor.moveToNext());
        }
        return schedules;
    }

    public void updateCalendar(Calendar calendar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,calendar.getId());
        values.put(KEY_NAME,calendar.getName());
        values.put(KEY_DESCRIPTION,calendar.getDescription());
        values.put(KEY_DATE, calendar.getDate());
        values.put(KEY_TIME,calendar.getTime());
        db.update(TABLE_CALENDAR,values,KEY_ID+" =?",new String[]{String.valueOf(calendar.getId())});
        db.close();
    }

    public void updateLesson(Lesson lesson){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,lesson.getId());
        values.put(KEY_NAME,lesson.getName());
        values.put(KEY_TEACHER,lesson.getTeacher());
        values.put(KEY_HOURS,lesson.getHours());
        values.put(KEY_TYPE,lesson.getType());
        values.put(KEY_SEMESTER,lesson.getSemester());
        db.update(TABLE_LESSONS,values,KEY_ID+" =?",new String[]{String.valueOf(lesson.getId())});
        db.close();
    }

    public void updateSession(Session session){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,session.getId());
        values.put(KEY_LESSON,session.getLesson());
        values.put(KEY_TYPE,session.getType());
        values.put(KEY_DATE,session.getDate());
        values.put(KEY_TIME,session.getTime());
        values.put(KEY_CLASSROOM,session.getClassroom());
        db.update(TABLE_SESSION,values,KEY_ID+" =?",new String[]{String.valueOf(session.getId())});
        db.close();
    }

    public void updatePayment(Payment payment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,payment.getId());
        values.put(KEY_STUDENT,payment.getStudentID());
        values.put(KEY_CASH,payment.getCash());
        values.put(KEY_DATE,payment.getDate());
        db.update(TABLE_PAYMENT,values,KEY_ID+" =?",new String[]{String.valueOf(payment.getId())});
        db.close();
    }

    public void updateSchedule(Schedule schedule){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,schedule.getId());
        values.put(KEY_LESSON,schedule.getLesson());
        values.put(KEY_DATE,schedule.getDay());
        values.put(KEY_POSITION,schedule.getPosition());
        values.put(KEY_CLASSROOM,schedule.getClassroom());
        db.update(TABLE_SCHEDULE,values,KEY_ID+" =?",new String[]{String.valueOf(schedule.getId())});
        db.close();
    }

    public void updateStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,student.getId());
        values.put(KEY_NAME,student.getName());
        values.put(KEY_BIRTHDAY,student.getBirthday());
        values.put(KEY_HOMEADDRESS,student.getHomeAdress());
        values.put(KEY_CURRENTADDRESS,student.getCurrentAdress());
        values.put(KEY_PHONE,student.getPhone());
        values.put(KEY_COURSE,student.getCourse());
        values.put(KEY_FACULTY,student.getFaculty());
        values.put(KEY_EMAIL,student.getEmail());
        values.put(KEY_PRIVILEGE,student.getPrivilege());
        values.put(KEY_PARENTSNAME,student.getParentsName());
        values.put(KEY_PARENTSADRESS,student.getParentsAdress());
        values.put(KEY_ABOUTFAMILY,student.getAboutFamily());
        values.put(KEY_AVGRATING,student.getAvgRating());
        values.put(KEY_STUDYFORM,student.getStudyForm());
        db.update(TABLE_STUDENTS,values,KEY_ID+" =?",new String[]{String.valueOf(student.getId())});
        db.close();
    }

    public void upFaculty() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_STUDENTS+" SET "+KEY_COURSE +" = "+KEY_COURSE+" + 1");
        db.close();
    }
}
