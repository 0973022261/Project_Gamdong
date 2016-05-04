package com.example.inhm.gamdong;

import android.graphics.drawable.AnimationDrawable;

import java.util.TimerTask;

/**
 * Created by inhm on 2016-05-02.
 */
public class AnimationTimer extends TimerTask {
    AnimationDrawable animation;

    public AnimationTimer(AnimationDrawable animation) {
        this.animation = animation;
    }

    @Override
    public void run() {
        animation.start();
        this.cancel();
    }
}