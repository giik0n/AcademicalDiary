package com.babylon.alex.academicaldiary.AddActivities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Schedule;
import com.babylon.alex.academicaldiary.pojo.Session;

import java.util.ArrayList;
import java.util.Calendar;

public class AddSessionActivity extends AppCompatActivity {
    //окно добавления сессии
    MyDatabaseHelper myDatabaseHelper;
    Spinner spinner;
    Button dateButton, timeButton, addButton;
    EditText type,classroom;
    String hour, minute, time;
    Calendar calendar;
    int id = 0;
    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);
        myDatabaseHelper = new MyDatabaseHelper(this);
        spinner = findViewById(R.id.addSessionSpinner);
        dateButton = findViewById(R.id.addSessionDateButton);
        timeButton = findViewById(R.id.addSessionTimeButton);
        addButton = findViewById(R.id.addSessionButton);
        type = findViewById(R.id.addSessionType);
        classroom = findViewById(R.id.addSessionClassroom);
        calendar = Calendar.getInstance();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<Lesson> lessons = (ArrayList<Lesson>) myDatabaseHelper.getLessons();
        ArrayList<String> lessonNames = new ArrayList<>();
        for (int i=0;i<lessons.size();i++){
            lessonNames.add(lessons.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lessonNames);
        spinner.setAdapter(adapter);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AddSessionActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String month = String.valueOf(i1+1);
                        String day = String.valueOf(i2);
                        if (i1 + 1 < 10) {
                            month = "0"+String.valueOf(i1+1);
                        }
                        if(i2<10){
                            day = "0"+String.valueOf(i2);
                        }
                        dateButton.setText(day+"."+month+"."+i);
                    }
                }, year, month, day);
                mDatePicker.setTitle(getString(R.string.choose_date));
                mDatePicker.show();
            }
        });

        hour = String.valueOf(calendar.get(Calendar.HOUR));
        minute = String.valueOf(calendar.get(Calendar.MINUTE));

        if(Integer.valueOf(hour)<10){
            hour = "0"+hour;
        }

        if(Integer.valueOf(minute)<10){
            minute = "0"+minute;
        }
        time = hour+":"+minute;

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(AddSessionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String hour = String.valueOf(i);
                        String minute = String.valueOf(i1);
                        if(Integer.valueOf(hour)<10){
                            hour = "0"+hour;
                        }
                        if(Integer.valueOf(minute)<10){
                            minute = "0"+minute;
                        }
                        String time = hour+":"+minute;
                        timeButton.setText(time);
                    }
                },calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session session = new Session(
                        id,
                        String.valueOf(lessons.get(spinner.getSelectedItemPosition()).getId()),
                        type.getText().toString(),
                        dateButton.getText().toString(),
                        timeButton.getText().toString(),
                        classroom.getText().toString());

                if (!editMode){
                myDatabaseHelper.addNewSession(session);
                Toast.makeText(AddSessionActivity.this, R.string.session_added_successfully, Toast.LENGTH_SHORT).show();
                }else {
                    myDatabaseHelper.updateSession(session);
                    Toast.makeText(AddSessionActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            editMode = true;
            Session session = extras.getParcelable("Object");
            spinner.setSelection(lessonNames.indexOf(session.getLesson()));
            dateButton.setText(session.getDate());
            timeButton.setText(session.getTime());
            type.setText(session.getType());
            classroom.setText(session.getClassroom());
            id = session.getId();

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
