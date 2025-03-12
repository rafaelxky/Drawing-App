package io.codeforall.kernelfc;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHandler {
    static Grid gridLocal;
    public static String fileURL = "Resources/SaveFile.txt";

    public static void saveString(CustomRect[][] rectsArray) throws IOException {
        FileWriter fiwr = new FileWriter(fileURL);
        for (int i = 0; i < rectsArray.length - 1; i++) {
            CustomRect[] rectArr = rectsArray[i];
            //for (CustomRect rect : rectArr) {
            for (int j = 0; j < rectArr.length - 1; j++) {
                CustomRect rect = rectArr[j];
                String finalString = i + "." + j + ".";
                if (rect.isFilled()) {
                    finalString += "1\n";
                } else {
                    continue;
                }
                fiwr.write(finalString);
            }
        }
        fiwr.close();
    }

    public static ArrayList<String> loadString() throws IOException {
        FileReader fire = new FileReader(fileURL);
        BufferedReader bufre = new BufferedReader(fire);
        ArrayList<String> returnString = new ArrayList<>();

        while (true) {
            String line = bufre.readLine();
            if (line != null) {
                returnString.add(line);
                continue;
            }
            break;
        }
        return returnString;
    }

    public static void storeGrid(Grid grid) {
        gridLocal = grid;
    }

    public static void loadToGrid(ArrayList<String> inList) {
        for (int i = 0; i < inList.size(); i++) {
            String[] separateIn = inList.get(i).split("\\.");
            gridLocal.fillRaw(Integer.parseInt(separateIn[1]), Integer.parseInt(separateIn[0]));
        }
    }
}
