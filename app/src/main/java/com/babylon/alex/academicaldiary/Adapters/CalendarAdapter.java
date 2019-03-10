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

import com.babylon.alex.academicaldiary.AddActivities.AddCalendarActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddScheduleActivity;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Calendar;
import com.babylon.alex.academicaldiary.pojo.Lesson;
import com.babylon.alex.academicaldiary.pojo.Schedule;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends BaseAdapter implements Filterable {

    Activity activity;
    List<Calendar> list;
    ArrayList<Calendar> filterList;
    LayoutInflater layoutInflater;
    MyDatabaseHelper myDatabaseHelper;
    CustomFilter filter;

    public CalendarAdapter(Activity activity, List<Calendar> list) {
        this.activity = activity;
        this.list = list;
        this.filterList = (ArrayList<Calendar>) list;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        view = layoutInflater.inflate(R.layout.calendar_item, null);
        TextView name = view.findViewById(R.id.calendarName);
        TextView description = view.findViewById(R.id.calendarDescription);
        TextView date = view.findViewById(R.id.calendarDate);
        TextView time = view.findViewById(R.id.calendarTime);
        ImageView more = view.findViewById(R.id.calendarMore);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(activity, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.popup_edit:
                                Intent intent = new Intent(activity, AddCalendarActivity.class);
                                intent.putExtra("Object",(Calendar)list.get(i));
                                activity.startActivity(intent);
                                break;
                            case R.id.popup_delete:
                                myDatabaseHelper.deleteCalendar(list.get(i));
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
        description.setText(list.get(i).getDescription());
        date.setText(list.get(i).getDate());
        time.setText(list.get(i).getTime());
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    public List<Calendar> getList() {
        return list;
    }


    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Calendar> filters = new ArrayList<>();

                for (int i = 0; i<filterList.size();i++){
                    if (filterList.get(i).getName().toUpperCase().contains(constraint)){
                        Calendar s = filterList.get(i);
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
            list = (List<Calendar>) results.values;
            notifyDataSetChanged();
        }
    }
}
