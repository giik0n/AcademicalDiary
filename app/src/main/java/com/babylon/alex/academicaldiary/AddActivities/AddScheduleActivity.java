package com.babylon.alex.academicaldiary.AddActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Schedule;

import java.util.ArrayList;
import java.util.Arrays;

public class AddScheduleActivity extends AppCompatActivity {
    MyDatabaseHelper myDatabaseHelper;
    Spinner lesson, day;
    NumberPicker position;
    EditText classroom;
    Button button;
    int id = 0;
    boolean editMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lesson = findViewById(R.id.addScheduleLesson);
        day = findViewById(R.id.addScheduleDay);
        position = findViewById(R.id.addSchedulePosition);
        classroom = findViewById(R.id.addScheduleClassroom);
        button = findViewById(R.id.AddScheduleButton);
        position.setMaxValue(8);
        position.setMinValue(1);
        position.setWrapSelectorWheel(false);
        final ArrayList<String> daysOfWeek = new ArrayList<String>(Arrays.asList(getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday)));
        myDatabaseHelper = new MyDatabaseHelper(this);
        ArrayList<Lesson> lessons = (ArrayList<Lesson>) myDatabaseHelper.getLessons();
        ArrayList<String> lessonNames = new ArrayList<>();
        for (int i=0;i<lessons.size();i++){
            lessonNames.add(lessons.get(i).getName());
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, daysOfWeek);
        day.setAdapter(dayAdapter);
        ArrayAdapter<String> lessonAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lessonNames);
        lesson.setAdapter(lessonAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule schedule = new Schedule(id, lesson.getSelectedItem().toString(),day.getSelectedItem().toString(), String.valueOf(position.getValue()),classroom.getText().toString());
                if (!editMode){
                    myDatabaseHelper.addNewSchedule(schedule);
                    Toast.makeText(AddScheduleActivity.this, "New lesson added to schedule successfully ", Toast.LENGTH_SHORT).show();
                }else{
                    myDatabaseHelper.updateSchedule(schedule);
                    Toast.makeText(AddScheduleActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            editMode = true;
            Schedule schedule = extras.getParcelable("Object");
            lesson.setSelection(lessonNames.indexOf(schedule.getLesson()));
            day.setSelection(daysOfWeek.indexOf(schedule.getDay()));
            position.setValue(Integer.valueOf(schedule.getPosition()));
            classroom.setText(schedule.getClassroom());
            id = schedule.getId();

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
