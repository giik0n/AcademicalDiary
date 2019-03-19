package com.babylon.alex.academicaldiary.AddActivities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Payment;
import com.babylon.alex.academicaldiary.pojo.Student;

import java.util.ArrayList;
import java.util.Calendar;

public class AddPaymentActivity extends AppCompatActivity {
//окно добавления оплати
    MyDatabaseHelper myDatabaseHelper;
    Spinner spinner;
    EditText cash;
    Button date, addButton;
    int id = 0;
    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        myDatabaseHelper = new MyDatabaseHelper(this);
        spinner = findViewById(R.id.addPaymentStudent);
        date = findViewById(R.id.addPaymentDate);
        addButton = findViewById(R.id.addPaymentAdd);
        cash = findViewById(R.id.addPaymentCash);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Student> students = (ArrayList<Student>) myDatabaseHelper.getStudents();

        ArrayList<String> studentNames = new ArrayList<>();
        for (int i=0;i<students.size();i++){
            studentNames.add(students.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, studentNames);
        spinner.setAdapter(adapter);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AddPaymentActivity.this, new DatePickerDialog.OnDateSetListener() {

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
                        date.setText(day+"."+month+"."+i);
                    }
                }, year, month, day);
                mDatePicker.setTitle(getString(R.string.choose_date));
                mDatePicker.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Payment payment = new Payment(id, spinner.getSelectedItem().toString(), cash.getText().toString(), date.getText().toString());
                if (!editMode) {
                    myDatabaseHelper.addNewPayment(payment);
                    Toast.makeText(AddPaymentActivity.this, R.string.new_payment_added_successfully, Toast.LENGTH_SHORT).show();
                }else {
                    myDatabaseHelper.updatePayment(payment);
                    Toast.makeText(AddPaymentActivity.this, R.string.updated, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            editMode = true;
            Payment payment = extras.getParcelable("Object");
            spinner.setSelection(studentNames.indexOf(payment.getStudentID()));
            date.setText(payment.getDate());
            cash.setText(payment.getCash());
            id = payment.getId();
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
