package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.Serializable;
import java.lang.reflect.Field;

public class CustomRect extends Rectangle implements Serializable {

    public CustomRect(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public String toString() {
        return "CustomRect{" +
                "x - " + super.getX() +
                "y - " + super.getY() +
                "}";
    }
}
