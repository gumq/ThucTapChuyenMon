package com.tranlequyen.appdubaothoitiet.weatherapp.db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tranlequyen.appdubaothoitiet.CityActivity;
import com.tranlequyen.appdubaothoitiet.R;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private CityActivity context;
    private int layout;
    private List<TaskCity> taskCityList;

    public TaskAdapter(CityActivity context, int layout, List<TaskCity> taskCityList) {
        this.context = context;
        this.layout = layout;
        this.taskCityList = taskCityList;

    }
    private static class ViewHolder{

        TextView txtTaskName;
        ImageView imvDelete, imvEdit,imvDes;
    }
    @Override
    public int getCount() {
        return taskCityList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskCityList.get(i).getTaskName ();
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
            holder.imvDes = view.findViewById ( R.id.imvdes );
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final TaskCity t = taskCityList.get(i);
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



}
