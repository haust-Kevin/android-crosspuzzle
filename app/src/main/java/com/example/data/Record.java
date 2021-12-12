package com.example.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.activity.GameActivity;

public class Record {
    private SharedPreferences shp;
    private final static String SAVE_SHP_DATA_NAME = "SAVE_SHP_DATA_NAME";
    private static final String KEY_HIGH_SCORE = "KEY_HIGH_SCORE";

    public Record(Activity activity){
         shp = activity.getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
    }

    public void save(int score){
        if(score>maxScore()){
            SharedPreferences.Editor editor = shp.edit();
            editor.putInt(KEY_HIGH_SCORE, score);
            editor.apply();
        }
    }
    public int maxScore(){
        return shp.getInt(KEY_HIGH_SCORE, 0);
    }
}
