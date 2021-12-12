package com.example.game.drawable.impl;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.example.CrosspuzzleApplication;
import com.example.R;
import com.example.game.drawable.IDrawable4ColorMapper;

/**
 * 产生纯色的Drawable
 */
public class SolidColorMapper implements IDrawable4ColorMapper {

    public int get(int color) {
        switch (color) {
            case 0:
                return R.color.blank2;
            case 1:
                return R.color.color1;
            case 2:
                return R.color.color2;
            case 3:
                return R.color.color3;
            case 4:
                return R.color.color4;
            case 5:
                return R.color.color5;
            case 6:
                return R.color.color6;
            case 7:
                return R.color.color7;
            case 8:
                return R.color.color8;
            case 9:
                return R.color.color9;
            case 10:
                return R.color.color0;
            case 11:
                return R.color.mediumslateblue;
            case 12:
                return R.color.mediumseagreen;

            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Drawable map(int color) {
        return new ColorDrawable(CrosspuzzleApplication.getCurrentApp().getResources().getColor(get(color))  );
    }
}
