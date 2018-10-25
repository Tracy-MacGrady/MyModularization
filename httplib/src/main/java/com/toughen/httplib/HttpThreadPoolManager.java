package com.toughen.httplib;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpThreadPoolManager {
    private static HttpThreadPoolManager instance;

    public static HttpThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (HttpThreadPoolManager.class) {
                if (instance == null) instance = new HttpThreadPoolManager();
            }
        }
        return instance;
    }

    private HttpThreadPoolManager() {
        queue = new LinkedBlockingQueue<>();
        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), rejectedExecutionHandler);
        threadPoolExecutor.execute(startRunnable);
    }

    //声明任务队列----阻塞队列
    private LinkedBlockingQueue<Runnable> queue;
    //声明线程池
    private ThreadPoolExecutor threadPoolExecutor;
    //线程池中的线程超时后的处理
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                queue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    //添加任务
    private void addTaskToQueue(Runnable runnable) {
        if (queue != null) {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //开启执行任务
    private Runnable startRunnable = new Runnable() {
        @Override
        public void run() {
            if (queue != null) {
                while (true) {
                    try {
                        Runnable task = queue.take();
                        if (task != null) threadPoolExecutor.execute(task);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

}
