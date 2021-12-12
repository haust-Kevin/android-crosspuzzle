package com.example.game.generator;

import java.util.List;

public interface IColorGenerator {
    /**
     * 产生一个 rowNum * columnNum 大小的颜色数组
     * @param rowNum
     * @param columnNum
     * @param blankRate 空白块比率
     * @param colorNum 总的颜色数量 （不包括空白快）
     * @return
     */
    List<Integer> generate(int rowNum, int columnNum, double blankRate, int colorNum);
}
