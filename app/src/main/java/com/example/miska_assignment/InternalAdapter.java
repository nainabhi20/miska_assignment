package com.example.miska_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class InternalAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<String> extras;
    Map<String,ArrayList<String>> map1;
    String country_names;
    public InternalAdapter(Context context, ArrayList<String> extras, Map<String,ArrayList<String>> map1,String country_names){
        this.context=context;
        this.extras=extras;
        this.map1=map1;
        this.country_names=country_names;
    }
    @Override
    public int getGroupCount() {
        return extras.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 4;
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
        TextView textView=(TextView)view.findViewById(R.id.text);
        textView.setText("Languages");
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(  android.R.layout.simple_list_item_1,parent,false);
        return view;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
