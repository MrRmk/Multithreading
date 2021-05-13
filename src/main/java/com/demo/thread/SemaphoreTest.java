package com.demo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 实现一个容器，提供两个方法，add、size，写两个线程：
 * 线程1： 添加10个元素到容器中
 * 线程2： 实时添加监控元素个数，当个数到5个时，线程2给出提示并结束
 */
public class SemaphoreTest {

    volatile List lists = new ArrayList<>();

    public void add(Object o){
        lists.add(o);
    }
    public int size(){
        return lists.size();
    }
    static Thread t1 = null, t2 = null;
    public static void main(String[] args) {
        SemaphoreTest c = new SemaphoreTest();
        Semaphore s = new Semaphore(1);

        t2 = new Thread(() -> {
            System.out.println("t2 启动");
            if (c.size() != 5) {

            }
            System.out.println("t2 结束");

        }, "t2");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1 = new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);
                if (c.size() == 5) {
                    t2.start();
                    try {
                        t2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t1 结束");
        }, "t1");
        t1.start();

    }


}
