package com.babylon.alex.academicaldiary.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.Adapters.ScheduleAdapter;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Payment;
import com.babylon.alex.academicaldiary.pojo.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    public ScheduleFragment() {

    }
    List<Object> data;
    MyDatabaseHelper myDatabaseHelper;
    ListView listView;
    List<Schedule> monday = new ArrayList<>();
    List<Schedule> tuesday = new ArrayList<>();
    List<Schedule> wednesday = new ArrayList<>();
    List<Schedule> thursday = new ArrayList<>();
    List<Schedule> friday = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        myDatabaseHelper = new MyDatabaseHelper(getActivity());
        data = new ArrayList<>();
        listView = view.findViewById(R.id.scheduleListView);
        refreshList();



        return view;
    }

    private void refreshList() {
        data.clear();
        monday = myDatabaseHelper.getScheduleByDay(getString(R.string.monday));
        tuesday = myDatabaseHelper.getScheduleByDay(getString(R.string.tuesday));
        wednesday = myDatabaseHelper.getScheduleByDay(getString(R.string.wednesday));
        thursday = myDatabaseHelper.getScheduleByDay(getString(R.string.thursday));
        friday = myDatabaseHelper.getScheduleByDay(getString(R.string.friday));
        if (monday.size() != 0){
            data.add(getString(R.string.monday));
            for(int i =0; i<monday.size();i++){
                data.add(monday.get(i));
            }
        }
        if (tuesday.size() != 0) {
            data.add(getString(R.string.tuesday));
            for (int i = 0; i < tuesday.size(); i++) {
                data.add(tuesday.get(i));
            }
        }
        if (wednesday.size() != 0){
            data.add(getString(R.string.wednesday));
            for(int i =0; i<wednesday.size();i++){
                data.add(wednesday.get(i));
            }
        }
        if (thursday.size() != 0){
            data.add(getString(R.string.thursday));
            for(int i =0; i<thursday.size();i++){
                data.add(thursday.get(i));
            }
        }
        if (friday.size() != 0) {
            data.add(getString(R.string.friday));
            for (int i = 0; i < friday.size(); i++) {
                data.add(friday.get(i));
            }
        }

        final ScheduleAdapter adapter1 = new ScheduleAdapter(getActivity(), data);
        listView.setAdapter(adapter1);
    }

    @Override
    public void onResume() {
        refreshList();
        super.onResume();
    }
    public ArrayList<Object> getCurrentList(){
        return (ArrayList<Object>) data;
    }

}
