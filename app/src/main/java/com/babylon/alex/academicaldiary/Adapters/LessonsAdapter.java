package com.babylon.alex.academicaldiary.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.AddActivities.AddLessonActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddScheduleActivity;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Schedule;
import com.babylon.alex.academicaldiary.pojo.Student;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends BaseAdapter implements Filterable{

    Activity activity;
    List<Lesson> list;
    ArrayList<Lesson> filterList;
    LayoutInflater layoutInflater;
    MyDatabaseHelper myDatabaseHelper;
    CustomFilter filter;

    public LessonsAdapter(Activity activity, List<Lesson> list) {
        this.activity = activity;
        this.list = list;
        this.filterList = (ArrayList<Lesson>) list;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.myDatabaseHelper = new MyDatabaseHelper(activity);
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
        view = layoutInflater.inflate(R.layout.lesson_item, null);
        TextView name = view.findViewById(R.id.lessonName);
        TextView teacher = view.findViewById(R.id.lessonTeacher);
        TextView type = view.findViewById(R.id.lessonType);
        TextView semester = view.findViewById(R.id.lessonSemester);
        TextView hours = view.findViewById(R.id.lessonHours);
        ImageView more = view.findViewById(R.id.lessonMore);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.popup_edit:
                                Intent intent = new Intent(activity, AddLessonActivity.class);
                                intent.putExtra("Object",(Lesson)list.get(i));
                                activity.startActivity(intent);
                                break;
                            case R.id.popup_delete:
                                myDatabaseHelper.deleteLesson(list.get(i));
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
        teacher.setText(list.get(i).getTeacher());
        type.setText(list.get(i).getType());
        semester.setText(list.get(i).getSemester());
        hours.setText(list.get(i).getHours());

        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    public List<Lesson> getList() {
        return list;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Lesson> filters = new ArrayList<>();

                for (int i = 0; i<filterList.size();i++){
                    if (filterList.get(i).getName().toUpperCase().contains(constraint)){
                        Lesson s = filterList.get(i);
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
            list = (List<Lesson>) results.values;
            notifyDataSetChanged();
        }
    }
}
