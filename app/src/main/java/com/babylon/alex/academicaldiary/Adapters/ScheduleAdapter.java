package com.babylon.alex.academicaldiary.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.babylon.alex.academicaldiary.AddActivities.AddScheduleActivity;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Schedule;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter{
    Activity activity;
    List<Object> list;
    LayoutInflater inflater;
    MyDatabaseHelper myDatabaseHelper;

    public static final int SCHEDULE_ITEM = 0;
    public static final int HEADER = 1;

    public ScheduleAdapter(FragmentActivity activity, List<Object> list) {
        this.list = list;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myDatabaseHelper = new MyDatabaseHelper(activity);
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof Schedule) {
            return SCHEDULE_ITEM;
        }else{
            return HEADER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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

        if(view == null){
            switch (getItemViewType(i)){
                case SCHEDULE_ITEM:
                    view = inflater.inflate(R.layout.schedule_item,null);
                    break;
                case HEADER:
                    view = inflater.inflate(R.layout.schedule_header_item,null);
                    break;
            }
        }

        switch (getItemViewType(i)){
            case SCHEDULE_ITEM:
                TextView position = (TextView)view.findViewById(R.id.schedulePosition);
                TextView name = (TextView)view.findViewById(R.id.scheduleName);
                TextView classroom = (TextView)view.findViewById(R.id.scheduleClassroom);
                ImageView more = view.findViewById(R.id.scheduleMore);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(activity, view);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.popup_edit:
                                        Intent intent = new Intent(activity, AddScheduleActivity.class);
                                        intent.putExtra("Object",(Schedule)list.get(i));
                                        activity.startActivity(intent);
                                        break;
                                    case R.id.popup_delete:
                                        myDatabaseHelper.deleteSchedule((Schedule) list.get(i));
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
                classroom.setText(((Schedule)list.get(i)).getClassroom());
                name.setText(((Schedule)list.get(i)).getLesson());
                position.setText(((Schedule)list.get(i)).getPosition());
                break;
            case HEADER:
                TextView title = (TextView) view.findViewById(R.id.scheduleHeader);
                title.setText(((String)list.get(i)));
                break;
        }

        return view;
    }
}
