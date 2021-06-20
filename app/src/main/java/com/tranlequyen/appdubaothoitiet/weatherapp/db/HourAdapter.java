package com.tranlequyen.appdubaothoitiet.weatherapp.db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tranlequyen.appdubaothoitiet.R;

import java.util.ArrayList;

public class HourAdapter extends BaseAdapter {
    Context context;
    ArrayList<weatherHour> arrayList;

    public HourAdapter(Context context, ArrayList<weatherHour> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size ();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
        convertView = inflater.inflate ( R.layout.item_hourly_forecast, null );
       weatherHour thoitiet = arrayList.get ( position );

       TextView textViewgio =convertView.findViewById ( R.id.textViewhour );
        TextView txtviewngay = convertView.findViewById ( R.id.txtdayhour );
        TextView txtviewtrangthai = convertView.findViewById ( R.id.txtdeshour);
        TextView txtmax = convertView.findViewById ( R.id.txtmaxhour );
        TextView txtmin = convertView.findViewById ( R.id.txtminhour );
        ImageView imgvtrangthai = convertView.findViewById ( R.id.imagedeshour );
        txtviewngay.setText ( thoitiet.Day );
        textViewgio.setText ( thoitiet.Hour );
        txtviewtrangthai.setText ( thoitiet.Status );
        Glide.with(context).load("https://openweathermap.org/img/w/"+thoitiet.Image+".png").into(imgvtrangthai);
        txtmax.setText ( thoitiet.MaxTemp + "°C" );
        txtmin.setText ( thoitiet.MinTemp + "°C" );
        return convertView;
    }
}


