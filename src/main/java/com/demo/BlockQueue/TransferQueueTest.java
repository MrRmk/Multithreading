package com.demo.BlockQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;

public class TransferQueueTest {

    public static void main(String[] args) throws Exception{
        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.transfer("aaa");

    }
}
