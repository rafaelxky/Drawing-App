package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.IOException;

public class Program {

    // get rect overlaping with player
    public static Rectangle calcOverlap(Cursor cursor, Rectangle[][] gridArray) {
        return gridArray[cursor.getY() / cursor.getSize()][cursor.getX() / cursor.getSize()];
    }


    public static void startProgram() throws InterruptedException, IOException, ClassNotFoundException {
        Grid grid = new Grid(500, 500, 10);
        Cursor cursor = new Cursor(grid);
        FileHandler.storeGrid(grid);


        //gameloop
        while (true) {
            cursor.getKeyStateAndCallAction();
            cursor.drawCursor();
            Thread.sleep(20);
        }
    }
}
