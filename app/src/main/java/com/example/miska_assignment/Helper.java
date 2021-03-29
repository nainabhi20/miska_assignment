package com.example.miska_assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Helper extends SQLiteOpenHelper {
    Context context;
    public Helper(Context context){
        super(context,"ROOM",null,1);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(context, "Database created", Toast.LENGTH_SHORT).show();
        db.execSQL("CREATE TABLE DATA(NAME VARCHAR(50) PRIMARY KEY,CAPITAL VARCHAR(50) NOT NULL,REGION VARCHAR(20) NOT NULL,SUBREGION VARCHAR(50) NOT NULL,POPULATION INT NOT NULL)");
        db.execSQL("CREATE TABLE BORDERS(NAME VARCAHR(50) NOT NULL,BORDER VARCHAR(20),FOREIGN KEY(NAME) REFERENCES DATA(NAME))");
        db.execSQL("CREATE TABLE LANGUAGES(NAME VARCAHR(50) NOT NULL,LANG VARCHAR(20),FOREIGN KEY(NAME) REFERENCES DATA(NAME))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
