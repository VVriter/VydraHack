package com.vydra.death.screen.utils.animations;

import com.vydra.death.screen.utils.Timer;

public class EaseIn implements IAnimation {

    private Timer timer;
    private int x;
    private int y;

    public EaseIn() {
        timer = new Timer();
    }
    @Override
    public int getValue(int x, int y, int delay) {
        if (timer.hasPassedMs(delay)) {
            timer.reset();
            y--;
        }

        return y;
    }

}
