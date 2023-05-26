package com.vydra.death.screen.util.animations;

import com.vydra.death.screen.util.Timer;

public class EaseRight implements IAnimation {
    private Timer timer;
    private int x;
    private int y;

    public EaseRight() {
        timer = new Timer();
    }

    @Override
    public int getValue(int x, int y, int delay) {
        this.x = x;
        this.y = y;

        if (timer.hasPassedMs(delay)) {
            timer.reset();
            x++;
        }

        return x;
    }
}
