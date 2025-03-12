package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Grid {
    // grid has squares, rows, columns
    // screen width
    public Boolean isPainting;
    public Boolean isPaintingChecked = false;
    public int screenWidth = 500;
    // screen height
    public int screenHeight = 500;
    // square size
    public int squareSize = 50;
    public CustomRect[][] gridArray;
    public colors color = colors.BLACK;

    public Grid(int screenWidth, int screenHeight, int squareSize) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.squareSize = squareSize;
        gridArray = new CustomRect[screenHeight / squareSize][screenWidth / squareSize];
        createGrid();
    }

    // draw grid method
    public void createGrid() {
        // square array

        // for each height / square size
        // current Y++ for each loop
        for (int i = 0; i < screenHeight / squareSize; i++) {

            // for each width / square width
            // current X++ for each loop
            for (int j = 0; j < screenWidth / squareSize; j++) {
                // place square in currentX * square size and currentY * squareSize
                gridArray[i][j] = new CustomRect(j * squareSize, i * squareSize, squareSize, squareSize);
                gridArray[i][j].draw();
                gridArray[i][j].setColor(Color.BLACK);

            }
        }
    }

    // for each element in array, redraw
    public void drawGrid() {
        for (CustomRect[] rectInner : gridArray) {
            for (CustomRect rect : rectInner) {
                if (rect.isFilled()) {
                    rect.fill();
                    continue;
                }
                rect.draw();
            }
        }
    }

    public void clearGrid() {
        for (CustomRect[] rectInner : gridArray) {
            for (CustomRect rect : rectInner) {
                rect.draw();
            }
        }
    }


    public CustomRect[][] getGridList() {
        return gridArray;
    }


    public void setGridList(CustomRect[][] gridArray) {
        this.gridArray = gridArray;
    }

    public CustomRect getRect(int x, int y) {
        return gridArray[y / squareSize][x / squareSize];
    }

    public CustomRect getRectRaw(int x, int y) {
        return gridArray[y][x];
    }

    public void changeGridRectColor(int x, int y) {
        // get current Rect isFilled when pressed space
        // if isPaiting, paint
        CustomRect currentRect = getRect(x, y);
        if (isPainting) {
            currentRect.draw();
            return;
        }
        currentRect.fill();
    }

    public void fill(int x, int y) {
        CustomRect currentRect = getRect(x, y);
        currentRect.fill();
    }

    public void fillRaw(int x, int y) {
        CustomRect currentRect = getRectRaw(x, y);
        currentRect.fill();
    }

    //set boolean is painting
    public void setIsPaiting(int x, int y) {
        isPainting = getRect(x, y).isFilled();
    }

    public int getSquareSize() {
        return squareSize;
    }

    public void setSquareSize(int squareSize) {
        this.squareSize = squareSize;
    }

    public void isPaintingChecked(boolean b) {
        isPaintingChecked = b;
    }

    public void saveChanges() throws IOException, ClassNotFoundException {
        FileHandler.saveString(gridArray);
    }

    public void loadFile() throws IOException {
        FileHandler.loadString();
    }
}
