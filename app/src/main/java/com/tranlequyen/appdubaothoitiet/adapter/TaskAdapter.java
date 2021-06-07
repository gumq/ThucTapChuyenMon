package com.tranlequyen.appdubaothoitiet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tranlequyen.appdubaothoitiet.ui.acticity.CityActivity;
import com.tranlequyen.appdubaothoitiet.R;
import com.tranlequyen.appdubaothoitiet.db.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private CityActivity context;
    private int layout;
    private List<Task> taskList;

    public TaskAdapter(CityActivity context, int layout, List<Task> taskList) {
        this.context = context;
        this.layout = layout;
        this.taskList = taskList;
    }

    public TaskAdapter(CityActivity cityActivity, int item_row, ArrayList<Task> taskArrayList) {
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService( Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTaskName = view.findViewById(R.id.txtTaskName);
            holder.imvDelete = view.findViewById(R.id.imvDelete);
            holder.imvEdit = view.findViewById(R.id.imvEdit);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final Task t = taskList.get(i);
        holder.txtTaskName.setText(t.getTaskName());

        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.openDialogEditTask(t.getTaskId(), t.getTaskName());
            }
        });

        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.deleteTask(t.getTaskId(), t.getTaskName());
            }
        });

        return view;
    }

    private static class ViewHolder{
        TextView txtTaskName;
        ImageView imvDelete, imvEdit;
    }
}
