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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.AddActivities.AddScheduleActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddSessionActivity;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Schedule;
import com.babylon.alex.academicaldiary.pojo.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionAdapter extends BaseAdapter implements Filterable{


    Activity activity;
    List<Session> list;
    ArrayList<Session> filterList;
    LayoutInflater layoutInflater;
    MyDatabaseHelper myDatabaseHelper;
    CustomFilter filter;
    ArrayList<Lesson> lessons;

    public SessionAdapter(Activity activity, List<Session> list) {
        this.activity = activity;
        this.list = list;
        this.filterList = (ArrayList<Session>) list;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.myDatabaseHelper = new MyDatabaseHelper(activity);
        lessons = (ArrayList<Lesson>) myDatabaseHelper.getLessons();
    }

    public List<Session> getList() {
        return list;
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
        view = layoutInflater.inflate(R.layout.session_item, null);

        TextView lesson = view.findViewById(R.id.sessionLesson);
        String tmp = myDatabaseHelper.getLessonNameById(String.valueOf(list.get(i).getLesson()));
        if (tmp.length() == 0 || tmp.equals("")){
            lesson.setText("Lesson not found");
        }else{
            lesson.setText(tmp);
        }
        TextView type = view.findViewById(R.id.sessionType);
        TextView time = view.findViewById(R.id.sessionTime);
        TextView date = view.findViewById(R.id.sessionDate);
        TextView classroom = view.findViewById(R.id.sessionClassroom);
        ImageView more = view.findViewById(R.id.paymentMore);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.popup_edit:
                                Intent intent = new Intent(activity, AddSessionActivity.class);
                                intent.putExtra("Object",(Session)list.get(i));
                                activity.startActivity(intent);
                                break;
                            case R.id.popup_delete:
                                myDatabaseHelper.deleteSession(list.get(i));
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



        type.setText(list.get(i).getType());
        time.setText(list.get(i).getTime());
        date.setText(list.get(i).getDate());
        classroom.setText(list.get(i).getClassroom());


        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Session> filters = new ArrayList<>();

                for (int i = 0; i<filterList.size();i++){
                    if (lessons.get(i).getName().toUpperCase().contains(constraint)){
                        Session s = filterList.get(i);
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
            list = (List<Session>) results.values;
            notifyDataSetChanged();
        }

    }
}
