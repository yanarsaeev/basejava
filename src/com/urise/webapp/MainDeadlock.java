package com.urise.webapp;

public class MainDeadlock {
    public static final Object object1 = new Object();
    public static final Object object2 = new Object();

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }
}

class Thread1 extends Thread {
    @Override
    public void run() {
        System.out.println("Thread1: попытка взять object1");
        synchronized (MainDeadlock.object1) {
            System.out.println("Thread1: object1 взят");
            System.out.println("Thread1: попытка взять object2");
            synchronized (MainDeadlock.object2) {
                System.out.println("Thread1: object2 взят");
            }
        }
    }
}

class Thread2 extends Thread {
    @Override
    public void run() {
        System.out.println("Thread2: попытка взять object2");
        synchronized (MainDeadlock.object2) {
            System.out.println("Thread2: object2 взят");
            System.out.println("Thread2: попытка взять object1");
            synchronized (MainDeadlock.object1) {
                System.out.println("Thread2: object1 взят");
            }
        }
    }
}