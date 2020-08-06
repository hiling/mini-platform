package com.github.hiling.item;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testThread(){
        Long startTime = System.currentTimeMillis();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);//创建只有2个线程的线程池
        //存放结果的列表
        List<Future<Integer>> resultList = new ArrayList<>();
        //通过Random类生成一个随机数生成器
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(10);
            FactorialCalculator calculator = new FactorialCalculator(number);
            Future<Integer> result = executor.submit(calculator);
            resultList.add(result);
        }
        //创建一个循环来监控执行器的状态
        try {
            while (executor.getCompletedTaskCount() < resultList.size()) {
                System.out.printf("\n已完成的线程数量: %d\n", executor.getCompletedTaskCount());
                for (int i = 0; i < resultList.size(); i++) {
                    Future<Integer> result = resultList.get(i);
                    System.out.printf("第 %d 个线程 : 是否完成:%s\n", i, result.isDone());
                }
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("全部线程执行结束");
        try {
            for (int i = 0; i < resultList.size(); i++) {
                Future<Integer> result = resultList.get(i);
                Integer number = null;
                number = result.get();
                System.out.printf("第 %d 个线程 执行结果是: %d\n", i, number);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        executor.shutdown();
        Long endTime = System.currentTimeMillis();
        System.out.println("使用时间 = [" + (endTime - startTime) + "]");
    }

    public class FactorialCalculator implements Callable<Integer> {
        private int number;

        public FactorialCalculator(int number) {
            this.number = number;
        }

        //计算阶乘
        public Integer call() throws Exception {
            Integer result = 1;
            if (number == 0 || number == 1)
                result = 1;
            else {
                for (int i = 2; i <= number; i++) {
                    result *= i;
                    //为了演示效果，休眠20ms
                    Thread.sleep(20);
                }
            }
            System.out.printf("线程:%s," + number + "!= %d\n", Thread.currentThread().getName(), result);
            return result;
        }

    }

}
