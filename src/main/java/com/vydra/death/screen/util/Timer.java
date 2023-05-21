package com.vydra.death.screen.util;

public class Timer {
    private long time;

    public Timer() {
        time = System.currentTimeMillis();
    }

    public boolean hasPassedMs(double ms) {
        return System.currentTimeMillis() - time >= ms;
    }

    public boolean hasPassedS(double s) {
        return System.currentTimeMillis() - time >= (long) (s * 1000.0);
    }

    public long getMs(long time) {
        return time / 1000000L;
    }

    public void reset() {
        time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
