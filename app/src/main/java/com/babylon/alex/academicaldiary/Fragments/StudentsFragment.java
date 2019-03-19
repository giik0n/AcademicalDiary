package com.babylon.alex.academicaldiary.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.babylon.alex.academicaldiary.Adapters.StudentsAdapter;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends Fragment {
    public StudentsFragment() {}

    List<Student> data = new ArrayList<>();
    MyDatabaseHelper myDatabaseHelper;
    ListView listView;
    SearchView search;
    StudentsAdapter adapter;
    ImageView upFaculty;
// фрагмент со студентами
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);
            myDatabaseHelper = new MyDatabaseHelper(getActivity());
            listView = view.findViewById(R.id.studentsListView);
            search = view.findViewById(R.id.searchStudents);
            upFaculty = view.findViewById(R.id.upStudentsFaculty);
            upFaculty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDatabaseHelper.upFaculty();
                    refreshList();
                }
            });
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
        data = myDatabaseHelper.getStudents();
        adapter = new StudentsAdapter(getActivity(), data);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        refreshList();
        super.onResume();
    }

    public List<Student> getCurrentList(){
        return data;
    }
}
