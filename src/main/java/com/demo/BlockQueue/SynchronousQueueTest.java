package com.demo.BlockQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest {

    public static void main(String[] args) throws Exception{
        BlockingQueue<String> strs = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(strs.take());
//                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.put("aaa");
//        strs.put("bbb");
//        strs.add("ccc");
        System.out.println(strs.size());
    }
}
