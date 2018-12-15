package com.mnsoft.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class UserApplicationTests {

    //对要被测试性能的代码添加注解，说明该方法是要被测试性能的
    @Benchmark
    public int sleepAWhile() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        return 0;
    }

    @Test
    public void contextLoads() throws Exception{
        Options opt = new OptionsBuilder()
                .include(UserApplicationTests.class.getSimpleName())
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(100)
                .build();

        new Runner(opt).run();
    }

}
