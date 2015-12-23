package com.example.spinno.taxiappdriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spinno.taxiappdriver.R;

import java.util.ArrayList;

/**
 * Created by saifi45 on 10/7/2015.
 */

public class Mycarselection extends BaseAdapter {

    Context ctc;
    LayoutInflater inflater;
    ArrayList<String> ad;


    public Mycarselection(Context context, ArrayList<String> timmingarraylist2) {
        ctc=context;
        ad=timmingarraylist2;
        inflater = LayoutInflater.from(this.ctc);
    }

    @Override
    public int getCount() {
        return ad.size();
    }

    @Override
    public Object getItem(int position) {
       // Log.e("position", "Dish" + position);
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  class Holder{

        TextView tv1,tv2;
    }
    public void remove(int position) {
        ad.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView== null) {
            convertView = inflater.inflate(R.layout.itemlayoutforvehicles, null);
            holder = new Holder();
            convertView.setTag(holder);
        }
        else {
            holder = (Holder)convertView.getTag();
        }


        holder.tv1 = (TextView)convertView.findViewById(R.id.itemTextView);
        holder.tv1.setText("" + ad.get(position));

        return convertView;
    }
}
