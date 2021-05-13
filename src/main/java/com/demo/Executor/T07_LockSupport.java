package com.demo.Executor;

import java.util.concurrent.locks.LockSupport;

/**
 * 用两个线程，第一个线程是从1到26，第二个线程是从A一直到Z，然后让这两个线程做到同时运行，交替输出，顺序打印。
 */
public class T07_LockSupport {

    static Thread t1 = null, t2 = null;
    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(() -> {
            for(char c: aI){
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t2);
            }
        }, "t1");

        t2 = new Thread(() -> {
            for(char c: aC){
                System.out.print(c);
                LockSupport.unpark(t1);
                LockSupport.park();
            }
        }, "t2");

        t2.start();
        t1.start();
    }

}
