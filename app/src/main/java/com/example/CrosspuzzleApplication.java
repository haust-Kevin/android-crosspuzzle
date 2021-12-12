package com.example;

import android.app.Application;

import com.example.game.drawable.IDrawable4ColorMapper;
import com.example.game.drawable.impl.SolidColorMapper;
import com.example.game.generator.IColorGenerator;
import com.example.game.generator.impl.RandomIColorGenerator;

public class CrosspuzzleApplication extends Application {

    private static Application currentApp;

    @Override
    public void onCreate() {
        currentApp = this;
        super.onCreate();
    }


    static private IColorGenerator generator;

    static private IDrawable4ColorMapper mapper;

    static {
        generator = new RandomIColorGenerator();
        mapper = new SolidColorMapper();
    }

    public static IColorGenerator getGenerator() {
        return generator;
    }

    public static void setGenerator(IColorGenerator generator) {
        CrosspuzzleApplication.generator = generator;
    }

    public static IDrawable4ColorMapper getMapper() {
        return mapper;
    }

    public static void setMapper(IDrawable4ColorMapper mapper) {
        CrosspuzzleApplication.mapper = mapper;
    }

    public static Application getCurrentApp(){
        return currentApp;
    }
}
