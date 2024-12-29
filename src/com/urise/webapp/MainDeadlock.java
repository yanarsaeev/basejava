package com.urise.webapp;

public class MainDeadlock {
    public static final String lock1 = "lock 1";
    public static final String lock2 = "lock 2";

    public static void deadlock(String lock1, String lock2) {
        new Thread(() -> {
            System.out.println("попытка взять " + lock1);
            synchronized (lock1) {
                System.out.println(lock1 + " взят");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("попытка взять " + lock2);
                synchronized (lock2) {
                    System.out.println(lock2 + " взят");
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        deadlock(lock1, lock2);
        deadlock(lock2, lock1);
    }
}
