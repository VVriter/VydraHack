package com.vydra.death.screen.utils.animations;

import com.vydra.death.screen.utils.Timer;

public class EaseLeft implements IAnimation {
    private Timer timer;
    private int x;
    private int y;

    public EaseLeft() {
        timer = new Timer();
    }

    @Override
    public int getValue(int x, int y, int delay) {
        this.x = x;
        this.y = y;

        if (timer.hasPassedMs(delay)) {
            timer.reset();
            x--;
        }

        return x;
    }
}
