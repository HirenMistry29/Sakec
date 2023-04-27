package com.example.sakec3.Events;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import java.util.Random;

public class GradientUtils {
    public static GradientDrawable getRandomGradient() {
        int[] colors = {getRandomColor(),getRandomColor()};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR,colors);
        return gd;
    }
    public static int getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return Color.rgb(red,green,blue);
    }
}
