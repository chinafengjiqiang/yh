package com.yh.scheduled;

/**
 * Created by FQ.CHINA on 2016/12/13.
 */
public class Task1 {
    public static void main(String[] args){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("Hello ！！！");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
