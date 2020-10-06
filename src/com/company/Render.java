package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Render {

    public static void renderLine(BufferedImage img, double x1, double y1, double x2, double y2, Color color) {
        if ((int) x1 == (int) x2 && (int) y1 == (int) y2){
            img.setRGB((int) x1, (int) y2, color.getRGB());
            return;
        }
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        for (int x = (int) Math.min(x1, x2); x <= (int) Math.max(x1, x2); x++) {
            for (int y = (int) Math.min(y1, y2); y <= (int) Math.max(y1, y2); y++) {
                if (dx > dy) {
                    img.setRGB(x, (int) (((x - x1) * (y2 - y1) / (x2 - x1)) + y1), color.getRGB());
                } else {
                    img.setRGB((int) (((y - y1) * (x2 - x1) / (y2 - y1)) + x1), y, color.getRGB());
                }
            }
        }
    }

    public static void renderOBJTriangle(BufferedImage img, double x1, double y1, double x2, double y2, double x3, double y3, double l1, double l2, double l3){
        Vector AB = new Vector(new double[]{x2, y2}).sum(new Vector(new double[]{x1, y1}).scMult(-1));
        Vector AC = new Vector(new double[]{x3, y3}).sum(new Vector(new double[]{x1, y1}).scMult(-1));
        for (int x = (int) Math.min(x1, Math.min(x2, x3)); x <= Math.max(x1, Math.max(x2, x3)); x++) {
            for (int y = (int) Math.min(y1, Math.min(y2, y3)); y < Math.max(y1, Math.max(y2, y3)); y++) {
                Vector PA = new Vector(new double[]{x1, y1}).sum(new Vector(new double[]{x, y}).scMult(-1));
                Vector V = new Vector(new double[]{AB.get(0), AC.get(0), PA.get(0)}).CrossProd(new Vector(new double[]{AB.get(1), AC.get(1), PA.get(1)}));
                double u = (V.get(0)/V.get(2));
                double v = (V.get(1)/V.get(2));
                if (u + v <= 1 && u >= 0 && v >= 0){
                    double l = l1*u + l2*v + l3*(1-u-v);
                    img.setRGB(x, y, (int) l);
                }
            }
        }
        renderLine(img, x1, y1, x2, y2, Color.BLACK);
        renderLine(img, x2, y2, x3, y3, Color.BLACK);
        renderLine(img, x3, y3, x1, y1, Color.BLACK);
    }

}