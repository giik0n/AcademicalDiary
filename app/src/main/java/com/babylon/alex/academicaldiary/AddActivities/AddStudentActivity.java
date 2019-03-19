package com.babylon.alex.academicaldiary.AddActivities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Schedule;
import com.babylon.alex.academicaldiary.pojo.Student;

import java.util.Calendar;

public class AddStudentActivity extends AppCompatActivity {
    // окдно добавления студентов
    TextView birthday;
    EditText name, homeAddress, currentAddress, phone, course, faculty, email, privilege,
    parentsName, parentsAddress, aboutFamily, avgRating, studyForm;
    int id = 0;
    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(getApplicationContext());
        Button addNewStudent = findViewById(R.id.addStudentButton);

        birthday = findViewById(R.id.addStudentBirthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AddStudentActivity.this, new DatePickerDialog.OnDateSetListener() {

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
                        birthday.setText(day+"."+month+"."+i);
                    }
                }, year, month, day);
                mDatePicker.setTitle(getString(R.string.choose_date));
                mDatePicker.show();
            }
        });
        name = findViewById(R.id.addStudentName);
        homeAddress = findViewById(R.id.addStudentHomeAddress);
        currentAddress = findViewById(R.id.addStudentCurrentAddress);
        phone = findViewById(R.id.addStudentPhone);
        course = findViewById(R.id.addStudentCourse);
        faculty = findViewById(R.id.addStudentFaculty);
        email = findViewById(R.id.addStudentEmail);
        privilege = findViewById(R.id.addStudentPrivilege);
        parentsName = findViewById(R.id.addStudentParentsName);
        parentsAddress = findViewById(R.id.addStudentParentsAddress);
        aboutFamily = findViewById(R.id.addStudentAboutFamily);
        avgRating = findViewById(R.id.addStudentAvgRating);
        studyForm = findViewById(R.id.addStudentStudyForm);

        addNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student(
                        id,
                        name.getText().toString(),
                        birthday.getText().toString(),
                        homeAddress.getText().toString(),
                        currentAddress.getText().toString(),
                        phone.getText().toString(),
                        course.getText().toString(),
                        faculty.getText().toString(),
                        email.getText().toString(),
                        privilege.getText().toString(),
                        parentsName.getText().toString(),
                        parentsAddress.getText().toString(),
                        aboutFamily.getText().toString(),
                        avgRating.getText().toString(),
                        studyForm.getText().toString()
                );
               if (!editMode) {
                   myDatabaseHelper.addNewStudent(student);
                   Toast.makeText(AddStudentActivity.this, R.string.student_added_successfully, Toast.LENGTH_SHORT).show();
               }else{
                   myDatabaseHelper.updateStudent(student);
                   Toast.makeText(AddStudentActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
               }
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            editMode = true;
            Student student = extras.getParcelable("Object");
            birthday.setText(student.getBirthday());
            name.setText(student.getName());
            homeAddress.setText(student.getHomeAdress());
            currentAddress.setText(student.getCurrentAdress());
            phone.setText(student.getPhone());
            course.setText(student.getCourse());
            faculty.setText(student.getFaculty());
            email.setText(student.getFaculty());
            privilege.setText(student.getPrivilege());
            parentsName.setText(student.getParentsName());
            parentsAddress.setText(student.getParentsAdress());
            aboutFamily.setText(student.getAboutFamily());
            avgRating.setText(student.getAvgRating());
            studyForm.setText(student.getStudyForm());
            id = student.getId();

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
