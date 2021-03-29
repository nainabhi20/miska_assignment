package com.example.miska_assignment;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends BaseExpandableListAdapter {
    Context context;//create three variables and initialize it with constructor so that we can use the data we can do it directly by using static keyword in MainActivity
    ArrayList<String> country_names,extras;
    Map<String,ArrayList<String>> map,map1,map2;
    ArrayList<String> flag_urls;
    //we build a constructor to get the initialize the data when when we create an object of this class
    public MyAdapter(Context context, ArrayList<String> country_names, Map<String,ArrayList<String>> map,ArrayList<String> extras,Map<String,ArrayList<String>> map1,Map<String,ArrayList<String>> map2,ArrayList<String>flag_urls){
        this.context=context;
        this.country_names=country_names;
        this.map=map;
        this.map1=map1;
        this.map2=map2;
        this.extras=extras;
        this.flag_urls=flag_urls;
    }
    @Override
    public int getGroupCount() {
        return country_names.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_groupview,parent,false);
        TextView name=(TextView)view.findViewById(R.id.text);
        name.setText(country_names.get(groupPosition));
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        LayoutInflater inflater1=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_detail,parent,false);
           TextView capital, subregion, population,borders,language;
           ImageView imageView=(ImageView)view.findViewById(R.id.imageview);
           //i dont know to to insert image from url to android i searched but kind solution till now but i will fond the sulution
           capital = (TextView) view.findViewById(R.id.capital);
           subregion = (TextView) view.findViewById(R.id.subregion);
           population = (TextView) view.findViewById(R.id.population);
           borders=(TextView)view.findViewById(R.id.borders);
           language=(TextView)view.findViewById(R.id.languages);
           capital.setText(map.get(country_names.get(groupPosition)).get(0));
           subregion.setText(map.get(country_names.get(groupPosition)).get(1));
           population.setText(map.get(country_names.get(groupPosition)).get(2));
           if (map2.get(country_names.get(groupPosition)).size()==0){
               language.setText("No official language");
           }else{
               language.setText(map2.get(country_names.get(groupPosition)).get(0));
               for (int i = 1; i < map2.get(country_names.get(groupPosition)).size(); i++) {
                   borders.setText(language.getText().toString() + "," + map2.get(country_names.get(groupPosition)).get(i));
               }
           }
        if (map1.get(country_names.get(groupPosition)).size()==0){
            borders.setText("No borders");
        }else {
            borders.setText(map1.get(country_names.get(groupPosition)).get(0));
            for (int i = 1; i < map1.get(country_names.get(groupPosition)).size(); i++) {
                borders.setText(borders.getText().toString() + "," + map1.get(country_names.get(groupPosition)).get(i));
            }
        }
           return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
