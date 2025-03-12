package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Cursor implements KeyboardHandler {
    public CustomRect cursor;
    public Keyboard keyboard;
    public Color cursorColor = Color.RED;
    private int size;
    private Grid grid;
    private HashMap<Integer, Boolean> keyStateMap = new HashMap<>();

    private long savedTime = System.currentTimeMillis();
    public int cursorDelay = 20;
    public int xMove = 0;
    public int yMove = 0;

    public final int up = KeyboardEvent.KEY_W;
    public final int left = KeyboardEvent.KEY_A;
    public final int down = KeyboardEvent.KEY_S;
    public final int right = KeyboardEvent.KEY_D;
    public final int save = KeyboardEvent.KEY_E;
    public final int draw = KeyboardEvent.KEY_SPACE;

    public Cursor(Grid grid) {
        size = grid.squareSize;
        this.cursor = new CustomRect(size, size, size, size);
        this.grid = grid;
        drawCursor();
        keyboard = new Keyboard(this);
        startKeyboardEvent();
    }


    // draws the cursor
    public void drawCursor() {
        cursor.setColor(cursorColor);
        cursor.fill();
    }


    // method to call the creation of the key press
    public void startKeyboardEvent() {
        ArrayList<Integer> keys = new ArrayList<>();
        keys.add(right);
        keys.add(left);
        keys.add(up);
        keys.add(down);
        keys.add(draw);
        keys.add(KeyboardEvent.KEY_C);
        keys.add(save);
        keys.add(KeyboardEvent.KEY_L);
        keys.add(KeyboardEvent.KEY_Q);

        for (int key : keys) {
            generateEvents(key);
        }
    }

    // method that automatically creates the release keyBoardEvents for each key
    public void autoStartKeysReleaseEvents(int key) {
        KeyboardEvent keyboardEvent = new KeyboardEvent();
        keyboardEvent.setKey(key);
        keyboardEvent.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
        keyboard.addEventListener(keyboardEvent);
    }


    // method to facilitate creating keyBoard events
    private void generateEvents(int key) {
        KeyboardEvent keyboardEvent = new KeyboardEvent();
        keyboardEvent.setKey(key);
        keyboardEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEvent);
        keyStateMap.put(key, false);
        autoStartKeysReleaseEvents(key);
    }

    //method to move
    public void move(int x, int y) {
        long currentTime = System.currentTimeMillis();
        if (isNextStepOutOfGrid(x, y) && delay(cursorDelay)) {
            cursor.translate(x * size, y * size);
        }
    }

    //
    public boolean isNextStepOutOfGrid(int x, int y) {
        if (
                cursor.getX() + x > grid.screenWidth - grid.getSquareSize() || // right
                        cursor.getX() + x < 0 || // left
                        cursor.getY() + y > grid.screenHeight - grid.getSquareSize() || // bottom
                        cursor.getY() + y < 0 // top
        ) {
            return false;
        }
        return true;

    }

    // change the value of the key in the HashMap
    public void changeKeyState(int key, boolean isActive) {
        keyStateMap.replace(key, isActive);
    }

    // gets the active keys and calls their action
    public void getKeyStateAndCallAction() throws IOException, ClassNotFoundException {
        for (Object key : keyStateMap.keySet().toArray()) {
            Integer keyInt = (Integer) key;

            if (keyStateMap.get(keyInt)) {
                keyActions(keyInt);
            }

        }

    }

    // keeps the keys actions
    public void keyActions(Integer keyPressed) throws IOException, ClassNotFoundException {
        // available keys array
        // loop
        switch (keyPressed) {

            case left -> {
                System.out.println("left");
                //move(-1, 0);
                xMove = -1;
            }
            case right -> {
                System.out.println("right");
                //move(1, 0);
                xMove = 1;
            }
            case up -> {
                System.out.println("up");
                //move(0, -1);
                yMove = -1;
            }
            case down -> {
                System.out.println("down");
                //move(0, 1);
                yMove = 1;
            }
            case draw -> {
                System.out.println("space");
                // reverse square color
                if (!grid.isPaintingChecked) {
                    grid.setIsPaiting(getX(), getY());
                    grid.isPaintingChecked = true;
                }
                grid.changeGridRectColor(getX(), getY());
            }
            case KeyboardEvent.KEY_C -> {
                System.out.println("c");
                grid.clearGrid();
            }
            case save -> {
                System.out.println("s");
                grid.saveChanges();
            }
            case KeyboardEvent.KEY_L -> {
                System.out.println("l");
                FileHandler.loadToGrid(FileHandler.loadString());
            }
            case KeyboardEvent.KEY_Q -> {
                System.out.println("q");
                if (cursorDelay == 1 && keyStateMap.get(KeyboardEvent.KEY_Q)) {
                    cursorDelay = 50;
                } else if (keyStateMap.get(KeyboardEvent.KEY_Q))
                    cursorDelay = 1;
                keyStateMap.replace(KeyboardEvent.KEY_Q, false);
            }
        }
        move(xMove, yMove);
    }

    // if key pressed make the value true in the hashMap
    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        changeKeyState(keyboardEvent.getKey(), true);
    }


    // if key released make the value false in the hashMap
    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        changeKeyState(keyboardEvent.getKey(), false);

        // if key is space and space is false
        if (keyboardEvent.getKey() == draw && !keyStateMap.get(draw)) {
            grid.isPaintingChecked(false);
        }
        switch (keyboardEvent.getKey()) {
            case left, right -> {
                xMove = 0;
            }
            case up, down -> {
                yMove = 0;
            }
        }
    }

    public boolean delay(int delayTime) {
        if (System.currentTimeMillis() - savedTime > delayTime) {
            savedTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return cursor.getX();
    }

    public int getY() {
        return cursor.getY();
    }

    public int getCursorDelay() {
        return cursorDelay;
    }

    public void setCursorDelay(int cursorDelay) {
        this.cursorDelay = cursorDelay;
    }
}
