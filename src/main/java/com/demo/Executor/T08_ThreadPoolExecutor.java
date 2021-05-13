package com.demo.Executor;

import java.util.concurrent.*;

public class T08_ThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        /*for(int i=0; i<15; i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize()+", 队列中等待执行的任务数目：" + executor.getQueue().size()
            +", 已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        }
        //关闭线程池
        executor.shutdown();*/

        /**
         * 4种线程池
         */
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);
       /* try {
            singleThreadExecutor.execute(new MyTask(1, "singleThreadExecutor"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       /* for(int j=0; j<5; j++){
            MyTask myTask = new MyTask(j, "fixedThreadPool-"+j);
            fixedThreadPool.execute(myTask);
        }*/

        for(int j=0; j<5; j++){
//            MyTask myTask = new MyTask(j, "cachedThreadPool-"+j);
            MyTask myTask = new MyTask(j);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(myTask);
        }

        /*for(int j=0; j<11; j++){
            MyTask myTask = new MyTask(j, "scheduledThreadPool-"+j);
            scheduledThreadPool.execute(myTask);
        }*/
        //关闭线程池
//        singleThreadExecutor.shutdown();
//        fixedThreadPool.shutdown();
        cachedThreadPool.shutdown();
//        scheduledThreadPool.shutdown();

    }


}
class MyTask implements Runnable{
    private int taskNum;
    private String name;
    public MyTask(int num){
        this.taskNum = num;
    }
    public MyTask(int num, String name){
        this.taskNum = num;
        this.name = name;
    }
    @Override
    public void run() {
        if(this.name != null && this.name.equals(""))
            Thread.currentThread().setName(this.name);
        System.out.println( Thread.currentThread().getName() + " 正在执行task " /*+ taskNum*/);
        try {
            Thread.currentThread().sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " task " + /*taskNum + */" 执行完毕......");
    }
}

