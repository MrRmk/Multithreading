package com.demo.Executor;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 假设您能够提供一个服务
 * 这个服务查询各大电商网站同一类产品的价格并汇总展示
 *
 */
public class T07_CompletableFuture {

    public static void main(String[] args) throws Exception {

        long start, end;
        start = System.currentTimeMillis();

        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceofTM());

        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceofTB());

        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(() -> priceofJD());

        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();

        end = System.currentTimeMillis();

        System.out.println("use completable future! " + (end - start));

        System.in.read();


    }

    private static double priceofTM(){
        delay("TM");
        return 1.00;
    }

    private static double priceofTB(){
        delay("TB");
        return 2.00;
    }

    private static double priceofJD(){
        delay("JD");
        return 3.00;
    }

    private static void delay(String str){
        int time = new Random().nextInt(5);
        System.out.printf("[%s] time is %s s\n", str, time);
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("After %s sleep! \n", time);
    }
}
