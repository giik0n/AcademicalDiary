package com.babylon.alex.academicaldiary.Fragments;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.Adapters.PaymentAdapter;
import com.babylon.alex.academicaldiary.Adapters.SessionAdapter;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class PaymentFragment extends Fragment {

    List<Payment> data = new ArrayList<>();
    MyDatabaseHelper myDatabaseHelper;
    ListView listView;
    SearchView search;
    PaymentAdapter adapter;

    public PaymentFragment() {
    }

// фргамент оплаты
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        myDatabaseHelper = new MyDatabaseHelper(getActivity());
        listView = view.findViewById(R.id.paymentListView);
        search = view.findViewById(R.id.searchPayment);
        refreshList();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {// поиск
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int i) {
                        data = adapter.getList();
                    }
                });
                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {// долгое нажатие для вызова напоминания
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View itemView = getLayoutInflater().inflate(R.layout.notify_layout,null);
                final TextView time = (TextView) itemView.findViewById(R.id.notifyTime);
                final TextView date = (TextView) itemView.findViewById(R.id.notifyDate);
                Button button = (Button) itemView.findViewById(R.id.notifyButton1);
                builder.setView(itemView);
                final AlertDialog dialog = builder.create();
                dialog.setTitle(R.string.addNotification);
                dialog.show();
                time.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {//установка времени
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                String hour = String.valueOf(selectedHour),
                                        minute = String.valueOf(selectedMinute);

                                if(selectedHour<10){
                                    hour = "0"+String.valueOf(selectedHour);
                                }

                                if(selectedHour>12){
                                    hour = String.valueOf(Integer.valueOf(hour));
                                }

                                if(selectedMinute<10){
                                    minute = "0"+String.valueOf(selectedMinute);
                                }

                                time.setText( hour + ":" + minute);
                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle(R.string.chooseTime);
                        mTimePicker.show();

                    }
                });

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {// установка дати
                        Calendar mcurrentTime = Calendar.getInstance();
                        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);
                        int month = mcurrentTime.get(Calendar.MONTH);
                        int year = mcurrentTime.get(Calendar.YEAR);
                        DatePickerDialog mDatePicker;
                        mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

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
                                date.setText(i+"-"+month+"-"+day);
                            }
                        }, year, month, day);
                        mDatePicker.setTitle(R.string.chooseDate);
                        mDatePicker.show();
                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {// добавить напоминание
                        if(!time.getText().toString().equals("") && !date.getText().toString().equals("")){
                            String dateText = date.getText().toString();
                            int day = Integer.valueOf(dateText.split("-")[2]);
                            int month = Integer.valueOf(dateText.split("-")[1]);
                            int year = Integer.valueOf(dateText.split("-")[0]);

                            String timeText = time.getText().toString();
                            AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
                            int hours = Integer.valueOf(timeText.split(":")[0]);
                            int minutes = Integer.valueOf(timeText.split(":")[1]);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.DAY_OF_MONTH, day);
                            calendar.set(Calendar.MONTH, month-1);
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.HOUR_OF_DAY, hours);
                            calendar.set(Calendar.MINUTE, minutes);
                            calendar.set(Calendar.SECOND,0);
                            calendar.set(Calendar.MILLISECOND,0);
                            Toast.makeText(getActivity(), "Додано\n"+calendar.getTime(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("singh.ajit.action.DISPLAY_NOTIFICATION");
                            intent.putExtra("Title","Payment");
                            intent.putExtra("Text",data.get(position).getStudentID()+" ("+data.get(position).getCash()+" | "+data.get(position).getDate()+")");
                            PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(),100,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),broadcast);
                        }
                        dialog.cancel();
                    }
                });
                return false;
            }
        });
        return view;
    }

    private void refreshList() {// обновление списка
        data = myDatabaseHelper.getPayment();
        adapter = new PaymentAdapter(getActivity(), data);
        listView.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        refreshList();
        super.onResume();
    }
    public List<Payment> getCurrentList(){
        return data;
    }

}
