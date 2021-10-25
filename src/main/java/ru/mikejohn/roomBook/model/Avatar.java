package ru.mikejohn.roomBook.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Component
public class Avatar {
    private final int cellCount = 5;
    private final int cellSize = 64;
    private final int padding = 32;
    private final Color backColor = new Color(241, 241, 241);;

    public BufferedImage generateImage() {
        int avatarLength = 2 * padding + cellCount * cellSize;
        int[][] data = generateMatrix(cellCount);

        BufferedImage avatarImage = new BufferedImage(avatarLength, avatarLength, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = avatarImage.createGraphics();

        g.setColor(backColor);
        g.fillRect(0, 0, avatarLength, avatarLength);
        g.setColor(generateColor());
        for (int i = 0; i < cellCount; i++) {
            for (int j = 0; j < cellCount; j++) {
                if (data[i][j] == 0) continue;
                g.fillRect(padding + i * cellSize, padding + j * cellSize, cellSize, cellSize);
            }
        }
        g.dispose();
        return avatarImage;
    }

    private Color generateColor() {
        Random random = new Random();
        int r, g, b;
        do {
            r = random.nextInt(255);
            g = random.nextInt(255);
            b = random.nextInt(255);
        } while (!checkColor(r, g, b));

        return new Color(r, g, b);
    }

    private static boolean checkColor(int r, int g, int b) {
        int bright = 210;
        int dark = 200;
        int gray = 30;

        // make the color bright
        if(r < bright && g < bright && g < bright) return false;
        // make the color dark
        if(r > dark && g > dark && b > dark) return false;
        // make the color less gray
        if(Math.abs(r - g) < gray && Math.abs(g - b) < gray && Math.abs(b - r) < gray) return false;

        return true;
    }

    public static int[][] generateMatrix(int cells){
        int halfSquare = (cells / 2) * cells;
        // control the amount of filled cells of the avatar
        int min = (int) Math.floor(0.3 * halfSquare);
        int max = (int) Math.ceil(0.8 * halfSquare);
        int count = 0;
        int[][] data = new int[cells][cells];
        Random random = new Random();
        do {
            count = 0;
            for (int i = 0; i < (cells + 1) / 2; i++) {
                for (int j = 0; j < cells; j++) {
                    count += data[i][j] = data[cells - 1 - i][j] = Math.abs(random.nextInt()) % 2;
                }
            }
        } while (count < min || count > max);

        return data;
    }
}
