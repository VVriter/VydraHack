package com.vydra.death.screen.gui;

public class Component {
    public double x;
    public double y;
    private int width;
    private int height;

    public Component(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(int mouseX, int mouseY) {
    }

    public void onClick(int mouseX, int mouseY, int state) {
    }

    public void setOffsets(final double minY) {
        this.y = minY;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setHeight(int h) {
        this.height =h ;
    }

    public int getHeight() {
        return this.height;
    }

    public int getOffsets() {
        return getHeight();
    }

    public int getWidth() {
        return this.width;
    }

}
