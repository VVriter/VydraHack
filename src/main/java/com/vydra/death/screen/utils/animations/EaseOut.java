package com.vydra.death.screen.utils.animations;

import com.vydra.death.screen.utils.Timer;

public class EaseOut implements IAnimation {

    private Timer timer;
    private int x;
    private int y;

    public EaseOut() {
        timer = new Timer();
    }

    @Override
    public int getValue(int x, int y, int delay) {
        this.x = x;
        this.y = y;

        if (timer.hasPassedMs(delay)) {
            timer.reset();
            y++;
        }

        return y;
    }
}
