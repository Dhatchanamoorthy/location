package com.example.second.getlocation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.deser.Deserializers;

import java.util.ArrayList;
import java.util.List;

import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    List<User> data = new ArrayList<User>();



    private Context context;
    private static LayoutInflater inflater=null;


    public CustomAdapter(Context context,List<User> data){


        this.data=data;
       this.context=context;
    }
    public int getCount() {
        return data.size();
    }


    public Object getItem(int position) {
        return null;
    }


    public long getItemId(int position) {
        return 0;
    }


public View getView(int position, View convertView, ViewGroup parent){

    LayoutInflater layoutInflater= ( LayoutInflater )context.
            getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View view=layoutInflater.inflate(R.layout.list,null);
    TextView userid=(TextView)view.findViewById(R.id.userid);

    TextView lat =(TextView)view.findViewById(R.id.lat);
    TextView longt=(TextView)view.findViewById(R.id.longt);
    TextView last=(TextView)view.findViewById(R.id.last);
    User user=data.get(position);
    userid.setText("UserId"+user.getUserId());
    lat.setText("Latitude"+user.getLatitude());
    longt.setText("Longitude"+user.getLongitude());
    last.setText("Lastupdate"+user.getLastUpdateTime());






return view;






}
}
