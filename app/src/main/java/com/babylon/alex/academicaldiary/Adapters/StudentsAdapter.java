package com.babylon.alex.academicaldiary.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.AddActivities.AddScheduleActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddStudentActivity;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Schedule;
import com.babylon.alex.academicaldiary.pojo.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentsAdapter extends BaseAdapter implements Filterable {
    Activity activity;
    List<Student> list;
    ArrayList<Student> filterList;
    LayoutInflater layoutInflater;
    MyDatabaseHelper myDatabaseHelper;
    CustomFilter filter;

    public StudentsAdapter(Activity activity, List<Student> list) {
        this.activity = activity;
        this.list = list;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.myDatabaseHelper = new MyDatabaseHelper(activity);
        this.filterList = (ArrayList<Student>) list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.student_item, null);
        TextView name = view.findViewById(R.id.studentName);
        TextView course = view.findViewById(R.id.studentCourse);
        TextView faculty = view.findViewById(R.id.studentFaculty);
        ImageView more = view.findViewById(R.id.studentMore);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.popup_edit:
                                Intent intent = new Intent(activity, AddStudentActivity.class);
                                intent.putExtra("Object",(Student)list.get(i));
                                activity.startActivity(intent);
                                break;
                            case R.id.popup_delete:
                                myDatabaseHelper.deleteStudent(list.get(i));
                                list.remove(i);
                                notifyDataSetChanged();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });

        name.setText(list.get(i).getName());
        course.setText(activity.getString(R.string.course)+list.get(i).getCourse());
        
        faculty.setText(activity.getString(R.string.faculty)+list.get(i).getFaculty());

        return view;
    }


    @Override
    public Filter getFilter() {

        if (filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    public List<Student> getList() {
        return list;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Student> filters = new ArrayList<>();

                for (int i = 0; i<filterList.size();i++){
                    if (filterList.get(i).getName().toUpperCase().contains(constraint)){
                        Student s = filterList.get(i);
                        filters.add(s);
                    }
                }
            results.count = filters.size();
                results.values = filters;
            }else{
                results.count = filterList.size();
                results.values = filterList;
            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (List<Student>) results.values;
            notifyDataSetChanged();
        }
    }
}
