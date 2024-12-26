package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private int counter;
    private static final Object object1 = new Object();
    private static final Object object2 = new Object();
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }
        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threadList = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threadList.add(thread);
        }

        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(mainConcurrency.counter);

        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }

    private synchronized void inc() {
        counter++;
    }
}

class Thread1 extends Thread {
    @Override
    public void run() {
        System.out.println("Thread1: попытка взять object1");
        synchronized (TestConc.object1) {
            System.out.println("Thread1: object1 взят");
            System.out.println("Thread1: попытка взять object2");
            synchronized (TestConc.object2) {
                System.out.println("Thread1: object2 взят");
            }
        }
    }
}

class Thread2 extends Thread {
    @Override
    public void run() {
        System.out.println("Thread2: попытка взять object2");
        synchronized (TestConc.object2) {
            System.out.println("Thread2: object2 взят");
            System.out.println("Thread2: попытка взять object1");
            synchronized (TestConc.object1) {
                System.out.println("Thread2: object1 взят");
            }
        }
    }
}
