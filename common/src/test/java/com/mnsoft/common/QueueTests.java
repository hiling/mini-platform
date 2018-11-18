package com.mnsoft.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/9/2018.
 */
@RunWith(SpringRunner.class)
public class QueueTests {

    public static ConcurrentHashMap<Integer,Integer> map = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

    final int totalTest=1000;
    AtomicInteger count = new AtomicInteger(0);
    final CountDownLatch latch = new CountDownLatch(totalTest);

    @Test
    public void queueTest() {
        System.out.println(Thread.currentThread().getName() + "启动");
        queueOffer();

        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void queueOffer() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    int n = count.getAndIncrement();
                    if (n >= totalTest) {
                        break;
                    }
                    //map.put(n,n);
                   queue.offer(n);
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
            long end = System.currentTimeMillis();
            System.out.println("准备数据耗时：" + (end - begin)+"  数量："+queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       //queuePoll();
    }

    private void queuePoll() {
        System.out.println(Thread.currentThread().getName() + "准备消费数据");
        long begin = System.currentTimeMillis();
        new Thread(() -> {
            while (!queue.isEmpty()) {
                //Thread.sleep((long) (Math.random() * 1000));
                Integer n = queue.poll();
                System.out.println(Thread.currentThread().getName() + "----------------------------->消费入数据:" + n);
            }
        }).start();
        long end = System.currentTimeMillis();
        //System.out.println("消费数据耗时：" + (end - begin));
    }
}
