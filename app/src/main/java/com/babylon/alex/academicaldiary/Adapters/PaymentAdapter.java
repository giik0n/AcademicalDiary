package com.babylon.alex.academicaldiary.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

import com.babylon.alex.academicaldiary.AddActivities.AddPaymentActivity;
import com.babylon.alex.academicaldiary.AddActivities.AddScheduleActivity;
import com.babylon.alex.academicaldiary.MyDatabaseHelper;
import com.babylon.alex.academicaldiary.R;
import com.babylon.alex.academicaldiary.pojo.Payment;
import com.babylon.alex.academicaldiary.pojo.Schedule;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends BaseAdapter implements Filterable {

    Activity activity;
    List<Payment> list;
    ArrayList<Payment> filterList;
    LayoutInflater layoutInflater;
    MyDatabaseHelper myDatabaseHelper;
    CustomFilter filter;

    public PaymentAdapter(Activity activity, List<Payment> list) {
        this.activity = activity;
        this.list = list;
        this.filterList = (ArrayList<Payment>) list;
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
        view = layoutInflater.inflate(R.layout.payment_item, null);

        TextView student = view.findViewById(R.id.paymentStudent);
        TextView cash = view.findViewById(R.id.paymentCash);
        TextView date = view.findViewById(R.id.paymentDate);
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
                                Intent intent = new Intent(activity, AddPaymentActivity.class);
                                intent.putExtra("Object",(Payment) list.get(i));
                                activity.startActivity(intent);
                                break;
                            case R.id.popup_delete:
                                myDatabaseHelper.deletePayment(list.get(i));
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

        cash.setText(list.get(i).getCash());
        date.setText(list.get(i).getDate());
        student.setText(list.get(i).getStudentID());

        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    public List<Payment> getList() {
        return list;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Payment> filters = new ArrayList<>();

                for (int i = 0; i<filterList.size();i++){
                    if (filterList.get(i).getStudentID().toUpperCase().contains(constraint)){
                        Payment s = filterList.get(i);
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
            list = (List<Payment>) results.values;
            notifyDataSetChanged();
        }
    }
}
