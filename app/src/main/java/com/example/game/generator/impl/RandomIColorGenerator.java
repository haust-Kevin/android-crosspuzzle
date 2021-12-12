package com.example.game.generator.impl;

import com.example.game.generator.IColorGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomIColorGenerator implements IColorGenerator {

    private int colorNum;
    private double blankRate;

    private final Random random = new Random();

    private int generate() {
        boolean isBlank = random.nextDouble() <= blankRate;
        if (isBlank) {
            return 0;
        } else {
            return random.nextInt(colorNum) + 1;
        }
    }

    @Override
    public List<Integer> generate(int rowNum, int columnNum, double blankRate, int colorNum) {
        this.colorNum = colorNum;
        this.blankRate = blankRate;
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                int color = generate();
                colors.add(color);
            }
        }
        return colors;
    }
}
