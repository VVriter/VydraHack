package com.vydra.death.screen.gui.click;

public interface IGuiComponent {
    int getWidth();
    int getHeight();
    int getX();
    int getY();

    void onClick(int x, int y, int state);
    void onHover(int x, int y);
    void draw();
    default void setY(int y) {

    }

    default void onKeyTyped(int keycode) {

    }
}
