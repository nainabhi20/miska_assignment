package com.example.miska_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> country_names;   //make of a array for storing the name of the all countres in asia
    Map<String,ArrayList<String>> map;//create a hash for strong data into childs of parent list view
    ArrayList<String> extras;
    ArrayList<String> flag_urls;
    Helper helper;
    SQLiteDatabase db;
    Map<String,ArrayList<String>> map1,map2;
    ExpandableListView listView;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper=new Helper(this);
        db=helper.getWritableDatabase();
        map=new HashMap<String, ArrayList<String>>();
        map1=new HashMap<String,ArrayList<String>>();
        map2=new HashMap<String, ArrayList<String>>();
        flag_urls=new ArrayList<String>();
        extras=new ArrayList<String>();
        extras.add("Borders");
        country_names=new ArrayList<String>();
        listView=(ExpandableListView)findViewById(R.id.listview);
        myAdapter=new MyAdapter(this,country_names,map,extras,map1,map2,flag_urls);
        listView.setAdapter(myAdapter);

        ImageView imageView=(ImageView)findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete the data from database permanantly");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.execSQL("DROP TABLE IF EXISTS DATA");
                        db.execSQL("DROP TABLE IF EXISTS BORDERS");
                        db.execSQL("DROP TABLE IF EXISTS LANGUAGES");
                        Toast.makeText(MainActivity.this, "Tables dropes", Toast.LENGTH_SHORT).show();
                        db.execSQL("CREATE TABLE IF NOT EXISTS DATA(NAME VARCHAR(50) PRIMARY KEY,CAPITAL VARCHAR(50) NOT NULL,REGION VARCHAR(20) NOT NULL,SUBREGION VARCHAR(50) NOT NULL,POPULATION INT NOT NULL)");
                        db.execSQL("CREATE TABLE IF NOT EXISTS BORDERS(NAME VARCAHR(50) NOT NULL,BORDER VARCHAR(20),FOREIGN KEY(NAME) REFERENCES DATA(NAME))");
                        db.execSQL("CREATE TABLE IF NOT EXISTS LANGUAGES(NAME VARCAHR(50) NOT NULL,LANG VARCHAR(20),FOREIGN KEY(NAME) REFERENCES DATA(NAME))");
                        Toast.makeText(MainActivity.this, "Empty Table created", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

        RequestQueue requestQueue;
        requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, "https://restcountries.eu/rest/v2/region/asia", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                   for (int i=0;i<response.length();i++){
                       ArrayList<String> expendable=new ArrayList<String>();
                       ArrayList<String> bord=new ArrayList<String>();
                       ArrayList<String> lang=new ArrayList<String>();
                       JSONObject object=response.getJSONObject(i);
                       for (int j=0;j<object.getJSONArray("borders").length();j++){
                           bord.add(object.getJSONArray("borders").getString(j));
                           ContentValues values=new ContentValues();
                           values.put("NAME",object.getString("name"));
                           values.put("BORDER",object.getJSONArray("borders").getString(j));
                           db.insert("BORDERS",null,values);
                       }
                       for (int j=0;j<object.getJSONArray("languages").length();j++){
                           lang.add(object.getJSONArray("languages").getJSONObject(j).getString("name"));
                           ContentValues values=new ContentValues();
                           values.put("NAME",object.getString("name"));
                           values.put("LANG",object.getJSONArray("languages").getJSONObject(j).getString("name"));
                           db.insert("LANGUAGES",null,values);
                       }
                       flag_urls.add(object.getString("flag"));
                       map2.put(object.getString("name"),lang);
                       map1.put(object.getString("name"),bord);
                       country_names.add(object.getString("name"));
                       expendable.add(object.getString("capital"));
                       expendable.add(object.getString("subregion"));
                       expendable.add(object.getString("population"));
                       map.put(object.getString("name"),expendable);

                       ContentValues values=new ContentValues();
                       values.put("NAME",object.getString("name"));
                       values.put("CAPITAL",object.getString("capital"));
                       values.put("REGION","ASIA");
                       values.put("SUBREGION",object.getString("subregion"));
                       values.put("POPULATION",object.getString("population"));
                       db.insert("DATA",null,values);
                       myAdapter.notifyDataSetChanged();
                   }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error detected",error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}