package com.babylon.alex.academicaldiary.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.babylon.alex.academicaldiary.Adapters.CalendarAdapter;
import com.babylon.alex.academicaldiary.Adapters.LessonsAdapter;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Calendar;
import com.babylon.alex.academicaldiary.pojo.Lesson;

import java.util.ArrayList;
import java.util.List;

public class StudyingWorkFragment extends Fragment {

    List<Calendar> data = new ArrayList<>();
    MyDatabaseHelper myDatabaseHelper;
    ListView listView;
    SearchView search;
    CalendarAdapter adapter;

    public StudyingWorkFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studying_work, container, false);
        myDatabaseHelper = new MyDatabaseHelper(getActivity());
        listView = view.findViewById(R.id.studyingWorkListView);
        search = view.findViewById(R.id.searchCalendar);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        refreshList();
        return view;
    }

    public void refreshList(){
        data = myDatabaseHelper.getCalendar();
        adapter = new CalendarAdapter(getActivity(), data);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        refreshList();
        super.onResume();
    }
    public List<Calendar> getCurrentList(){
        return data;
    }


}
