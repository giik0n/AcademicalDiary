package com.babylon.alex.academicaldiary.AddActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Schedule;

public class AddLessonActivity extends AppCompatActivity {
        // окно добавления уроков
    EditText name, teacher, hours, type, semester;
    Button button;
    MyDatabaseHelper myDatabaseHelper;
    int id = 0;
    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_lesson);
        myDatabaseHelper = new MyDatabaseHelper(this);
        name = findViewById(R.id.addLessonName);
        teacher = findViewById(R.id.addLessonTeacher);
        hours = findViewById(R.id.addLessonHours);
        type = findViewById(R.id.addLessonType);
        semester = findViewById(R.id.addLessonSemester);
        button = findViewById(R.id.addLessonButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lesson lesson = new Lesson(id,name.getText().toString(),
                        teacher.getText().toString(),hours.getText().toString(),type.getText().toString(),
                        semester.getText().toString());
                if (!editMode){
                    myDatabaseHelper.addNewLesson(lesson);
                    Toast.makeText(AddLessonActivity.this, R.string.lesson_added_successfully, Toast.LENGTH_SHORT).show();
                }else{
                    myDatabaseHelper.updateLesson(lesson);
                    Toast.makeText(AddLessonActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            editMode = true;
            Lesson lesson = extras.getParcelable("Object");
            name.setText(lesson.getName());
            teacher.setText(lesson.getTeacher());
            hours.setText(lesson.getHours());
            type.setText(lesson.getType());
            semester.setText(lesson.getSemester());
            id = lesson.getId();

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
