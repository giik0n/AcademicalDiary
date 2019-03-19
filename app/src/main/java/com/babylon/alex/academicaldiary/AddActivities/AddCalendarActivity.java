package com.babylon.alex.academicaldiary.AddActivities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Schedule;

import java.util.Calendar;

public class AddCalendarActivity extends AppCompatActivity {
    // окно добавления усчебних мероприятий
    MyDatabaseHelper myDatabaseHelper;
    EditText name, description;
    Button dateButton, timeButton, addButton;
    String hour, minute, time;
    Calendar calendar;
    int id = 0;
    boolean editMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDatabaseHelper = new MyDatabaseHelper(this);
        calendar = Calendar.getInstance();
        name = findViewById(R.id.addCalendarName);
        description = findViewById(R.id.addCalendarDescription);
        dateButton = findViewById(R.id.addCalendarDate);
        timeButton = findViewById(R.id.addCalendarTime);
        addButton = findViewById(R.id.addCalendar);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// вибор дати
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AddCalendarActivity.this, new DatePickerDialog.OnDateSetListener() {

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
            public void onClick(View view) {// вибор времени
                new TimePickerDialog(AddCalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            public void onClick(View view) {// добавление в бд
                com.babylon.alex.academicaldiary.pojo.Calendar calendar = new com.babylon.alex.academicaldiary.pojo.Calendar(
                        id,
                        name.getText().toString(),
                        description.getText().toString(),
                        dateButton.getText().toString(),
                        timeButton.getText().toString()
                );
                if (!editMode) {
                    myDatabaseHelper.addNewCalendar(calendar);
                    Toast.makeText(AddCalendarActivity.this, R.string.activity_added_successfully, Toast.LENGTH_SHORT).show();
                }else {
                    myDatabaseHelper.updateCalendar(calendar);
                    Toast.makeText(AddCalendarActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();

                }
            }

        });

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            editMode = true;
            com.babylon.alex.academicaldiary.pojo.Calendar calendar2 = extras.getParcelable("Object");
            name.setText(calendar2.getName());
            description.setText(calendar2.getDescription());
            dateButton.setText(calendar2.getDate());
            timeButton.setText(calendar2.getTime());
            id = calendar2.getId();

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
