package com.yh.scheduled;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by FQ.CHINA on 2016/12/13.
 */
public class Task2 {
    public static void main(String[] args){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello Task2 !!! ");
            }
        };

        Timer timer = new Timer("MyTimer");
        timer.scheduleAtFixedRate(task,0,1000);
    }
}
