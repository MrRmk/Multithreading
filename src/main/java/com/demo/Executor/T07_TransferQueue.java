package com.demo.Executor;

import com.demo.BlockQueue.TransferQueueTest;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class T07_TransferQueue {

    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();
        TransferQueue<Character> queue = new LinkedTransferQueue<Character>();

        new Thread(() -> {
            for(char c: aI){
                try {
                    System.out.println(queue.take());
                    queue.transfer(c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (char c: aC){
                try {
                    queue.transfer(c);
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}
