package com.babylon.alex.academicaldiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.AddActivities.AddCalendarActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddLessonActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddPaymentActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddScheduleActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddSessionActivity;
import com.babylon.alex.academicaldiary.Fragments.LessonsFragment;
import com.babylon.alex.academicaldiary.Fragments.PaymentFragment;
import com.babylon.alex.academicaldiary.Fragments.ScheduleFragment;
import com.babylon.alex.academicaldiary.Fragments.SessionFragment;
import com.babylon.alex.academicaldiary.Fragments.StudentsFragment;
import com.babylon.alex.academicaldiary.Fragments.StudyingWorkFragment;
import com.babylon.alex.academicaldiary.AddActivities.AddStudentActivity;
import com.babylon.alex.academicaldiary.pojo.Calendar;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Payment;
import com.babylon.alex.academicaldiary.pojo.Schedule;
import com.babylon.alex.academicaldiary.pojo.Session;
import com.babylon.alex.academicaldiary.pojo.Student;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction ft;

    LessonsFragment lessonsFragment;
    PaymentFragment paymentFragment;
    ScheduleFragment scheduleFragment;
    SessionFragment sessionFragment;
    StudentsFragment studentsFragment;
    StudyingWorkFragment studyingWorkFragment;

    MenuItem printButton;
    FloatingActionButton fab;
    private String currentFragment = "";

    private String name = "";
    MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);

    private final String studentFragmentString = "studentsFragment";
    private final String lessonsFragmentString = "lessonsFragment";
    private final String paymentFragmentString = "paymentFragment";
    private final String scheduleFragmentString = "scheduleFragment";
    private final String sessionFragmentString = "sessionFragment";
    private final String studyingWorkFragmentString = "studyingWorkFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lessonsFragment = new LessonsFragment();
        paymentFragment = new PaymentFragment();
        scheduleFragment = new ScheduleFragment();
        sessionFragment = new SessionFragment();
        studentsFragment = new StudentsFragment();
        studyingWorkFragment = new StudyingWorkFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentFragment){
                    case studentFragmentString:
                        startActivity(new Intent(MainActivity.this, AddStudentActivity.class));
                        break;
                    case lessonsFragmentString:
                        startActivity(new Intent(MainActivity.this, AddLessonActivity.class));
                        break;
                    case sessionFragmentString:
                        startActivity(new Intent(MainActivity.this, AddSessionActivity.class));
                        break;
                    case paymentFragmentString:
                        startActivity(new Intent(MainActivity.this, AddPaymentActivity.class));
                        break;
                    case studyingWorkFragmentString:
                        startActivity(new Intent(MainActivity.this, AddCalendarActivity.class));
                        break;
                    case scheduleFragmentString:
                        startActivity(new Intent(MainActivity.this, AddScheduleActivity.class));
                        break;
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        printButton = menu.findItem(R.id.action_print);
        printButton.setVisible(false);
        fab.hide();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose file name");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        switch (id){
            case R.id.action_settings:
            break;

            case R.id.action_print:
                switch (currentFragment){
                    case scheduleFragmentString:
                        final ArrayList<Object> listSchedule = scheduleFragment.getCurrentList();

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                name = input.getText().toString();

                                        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents/AcademicalDiary");
                                        if (!docsFolder.exists()) {
                                            docsFolder.mkdir();
                                            Toast.makeText(getApplicationContext(), "Created new Folder ", Toast.LENGTH_SHORT).show();
                                        }

                                        File pdfFile = new File(docsFolder.getAbsolutePath(),name+".pdf");
                                        try {
                                            Document document = new Document();
                                            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                                            document.open();
                                            for (int i = 0; i<listSchedule.size();i++){
                                                if (listSchedule.get(i) instanceof Schedule){
                                                    document.add(new Paragraph("Position:"+((Schedule) listSchedule.get(i)).getPosition() + " Lesson: "+((Schedule) listSchedule.get(i)).getLesson() + " Classroom: "+((Schedule) listSchedule.get(i)).getClassroom()+"\n"));
                                                }else {
                                                    document.add(new Paragraph("\n"+listSchedule.get(i).toString()));
                                                }
                                            }

                                            document.close();

                                        } catch (FileNotFoundException | DocumentException e) {
                                            e.printStackTrace();
                                        }
                                        previewPdf(pdfFile);
                            }
                        });
                        builder.show();
                        break;
                    case lessonsFragmentString:
                        final ArrayList<Lesson> list2 = (ArrayList<Lesson>) lessonsFragment.getCurrentList();

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                name = input.getText().toString();

                                File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents/AcademicalDiary");
                                if (!docsFolder.exists()) {
                                    docsFolder.mkdir();
                                    Toast.makeText(getApplicationContext(), "Created new Folder ", Toast.LENGTH_SHORT).show();
                                }

                                File pdfFile = new File(docsFolder.getAbsolutePath(),name+".pdf");
                                try {
                                    Document document = new Document();
                                    PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                                    document.open();
                                    for (int i = 0; i<list2.size();i++){
                                            document.add(new Paragraph("\nLesson: "+list2.get(i).getName()+"\nTeacher: "+list2.get(i).getTeacher()+
                                            "\nHours:"+list2.get(i).getHours()+"\nType:"+list2.get(i).getType()+"\nSemester"+list2.get(i).getSemester()+"\n"));
                                    }
                                    document.close();

                                } catch (FileNotFoundException | DocumentException e) {
                                    e.printStackTrace();
                                }
                                previewPdf(pdfFile);
                            }
                        });
                        builder.show();
                        break;
                    case sessionFragmentString:
                        final ArrayList<Session> list3 = (ArrayList<Session>) sessionFragment.getCurrentList();
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                name = input.getText().toString();

                                File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents/AcademicalDiary");
                                if (!docsFolder.exists()) {
                                    docsFolder.mkdir();
                                    Toast.makeText(getApplicationContext(), "Created new Folder", Toast.LENGTH_SHORT).show();
                                }

                                File pdfFile = new File(docsFolder.getAbsolutePath(),name+".pdf");
                                try {
                                    Document document = new Document();
                                    PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                                    document.open();
                                    for (int i = 0; i<list3.size();i++){
                                        document.add(new Paragraph("\nLesson: "+myDatabaseHelper.getLessonNameById(String.valueOf(list3.get(i).getLesson()))+"\nType: "+list3.get(i).getType()+
                                        "\nDate: "+list3.get(i).getDate()+"\nTime: "+list3.get(i).getTime()+"\nClassroom: "+list3.get(i).getClassroom()+"\n"));
                                    }
                                    document.close();

                                } catch (FileNotFoundException | DocumentException e) {
                                    e.printStackTrace();
                                }
                                previewPdf(pdfFile);
                            }
                        });

                        builder.show();
                        break;
                    case paymentFragmentString:
                        final ArrayList<Payment> list4 = (ArrayList<Payment>) paymentFragment.getCurrentList();
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                name = input.getText().toString();

                                File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents/AcademicalDiary");
                                if (!docsFolder.exists()) {
                                    docsFolder.mkdir();
                                    Toast.makeText(getApplicationContext(), "Created new Folder", Toast.LENGTH_SHORT).show();
                                }

                                File pdfFile = new File(docsFolder.getAbsolutePath(),name+".pdf");
                                try {
                                    Document document = new Document();
                                    PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                                    document.open();
                                    for (int i = 0; i<list4.size();i++){
                                        document.add(new Paragraph("\nStudent: "+list4.get(i).getStudentID()+"\nCash: "+list4.get(i).getCash()+"\nDate:"+list4.get(i).getDate()+"\n"));
                                    }
                                    document.close();

                                } catch (FileNotFoundException | DocumentException e) {
                                    e.printStackTrace();
                                }
                                previewPdf(pdfFile);
                            }
                        });
                        builder.show();
                        break;
                    case studyingWorkFragmentString:
                        final ArrayList<Calendar> list5 = (ArrayList<Calendar>) studyingWorkFragment.getCurrentList();
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                name = input.getText().toString();

                                File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents/AcademicalDiary");
                                if (!docsFolder.exists()) {
                                    docsFolder.mkdir();
                                    Toast.makeText(getApplicationContext(), "Created new Folder", Toast.LENGTH_SHORT).show();
                                }

                                File pdfFile = new File(docsFolder.getAbsolutePath(),name+".pdf");
                                try {
                                    Document document = new Document();
                                    PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                                    document.open();
                                    for (int i = 0; i<list5.size();i++){
                                        document.add(new Paragraph("\nActivity: "+list5.get(i).getName()+"\nDescription: "+list5.get(i).getDescription()+"\nDate:"+list5.get(i).getDate()+"\nTime: "+list5.get(i).getTime()+"\n"));
                                    }
                                    document.close();

                                } catch (FileNotFoundException | DocumentException e) {
                                    e.printStackTrace();
                                }
                                previewPdf(pdfFile);
                            }
                        });
                        builder.show();
                        break;
                    case studentFragmentString:
                        final ArrayList<Student> list6 = (ArrayList<Student>) studentsFragment.getCurrentList();
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                name = input.getText().toString();

                                File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents/AcademicalDiary");
                                if (!docsFolder.exists()) {
                                    docsFolder.mkdir();
                                    Toast.makeText(getApplicationContext(), "Created new Folder", Toast.LENGTH_SHORT).show();
                                }

                                File pdfFile = new File(docsFolder.getAbsolutePath(),name+".pdf");
                                try {
                                    Document document = new Document();
                                    PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                                    document.open();
                                    for (int i = 0; i<list6.size();i++){
                                        document.add(new Paragraph("\nName: "+list6.get(i).getName()+"\nBirthday: "+list6.get(i).getBirthday()+"\nHome Address:"+list6.get(i).getHomeAdress()+"\nCurrent Address: "+list6.get(i).getCurrentAdress()+
                                        "\nPhone: "+list6.get(i).getPhone()+"\nCourse: "+list6.get(i).getCourse()+"\nFaculty: "+list6.get(i).getFaculty()+
                                        "\nEmail: "+list6.get(i).getEmail()+"\nPrivilege: "+list6.get(i).getPrivilege()+"\nParents Name: "+list6.get(i).getParentsName()+
                                        "\nParents Address: "+list6.get(i).getParentsAdress()+"\nAbout Family: "+list6.get(i).getAboutFamily()+"\nAverage Rating: "+list6.get(i).getAvgRating()+
                                        "\nStudy form: "+ list6.get(i).getStudyForm()+"\n"));
                                    }
                                    document.close();

                                } catch (FileNotFoundException | DocumentException e) {
                                    e.printStackTrace();
                                }
                                previewPdf(pdfFile);
                            }
                        });
                        builder.show();
                        break;
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        ft = getSupportFragmentManager().beginTransaction();

        printButton.setVisible(true);
        fab.show();

        switch (id){
            case R.id.nav_students:
                ft.replace(R.id.container,studentsFragment);
                currentFragment = studentFragmentString;
                setTitle("Students");
                break;
            case R.id.nav_lessons:
                ft.replace(R.id.container,lessonsFragment);
                currentFragment = lessonsFragmentString;
                setTitle("Lessons");
                break;
            case R.id.nav_schedule:
                ft.replace(R.id.container,scheduleFragment);
                currentFragment = scheduleFragmentString;
                setTitle("Schedule");
                break;
            case R.id.nav_session:
                ft.replace(R.id.container,sessionFragment);
                currentFragment = sessionFragmentString;
                setTitle("Session");
                break;
            case R.id.nav_payment:
                ft.replace(R.id.container,paymentFragment);
                currentFragment = paymentFragmentString;
                setTitle("Payment");
                break;
            case R.id.nav_edu:
                ft.replace(R.id.container,studyingWorkFragment);
                currentFragment = studyingWorkFragmentString;
                setTitle("Educational Activities");
                break;
        }

        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callInput(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose file name");
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 name = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void previewPdf(File pdfFile) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
    }
}
