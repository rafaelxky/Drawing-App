package io.codeforall.kernelfc;

import java.awt.*;

public enum colors {
    NONE(null),
    BLACK(Color.BLACK),
    BLUE(Color.BLUE),
    RED(Color.RED);
    private final Color color;

    colors(Color color) {
        this.color = color;
    }
}
