package com.makarov;

public class Deadlock {

    public static void main(String[] args) {
        Object firstLock = new Object();
        Object secondLock = new Object();

        new Thread(() -> firstRun(firstLock, secondLock)).start();
        new Thread(() -> secondRun(firstLock, secondLock)).start();
    }

    public static void firstRun(Object firstLock, Object secondLock) {
        synchronized (firstLock) {
            try {
                Thread.sleep(50);
                synchronized (secondLock) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void secondRun(Object firstLock, Object secondLock) {
        synchronized (secondLock) {
            try {
                Thread.sleep(10);
                synchronized (firstLock) {
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
