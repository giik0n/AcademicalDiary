package com.babylon.alex.academicaldiary.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.babylon.alex.academicaldiary.Adapters.LessonsAdapter;
import com.babylon.alex.academicaldiary.Adapters.SessionAdapter;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionFragment extends Fragment {


    public SessionFragment() {
    }

    List<Session> data = new ArrayList<>();
    MyDatabaseHelper myDatabaseHelper;
    ListView listView;
    SearchView search;
    SessionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        myDatabaseHelper = new MyDatabaseHelper(getActivity());
        listView = view.findViewById(R.id.sessionListView);
        search = view.findViewById(R.id.searchSession);
        refreshList();

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
        return view;
    }

    public void refreshList(){
        data = myDatabaseHelper.getSession();
        adapter = new SessionAdapter(getActivity(), data);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        refreshList();
        super.onResume();
    }

    public List<Session> getCurrentList(){
        return data;
    }


}