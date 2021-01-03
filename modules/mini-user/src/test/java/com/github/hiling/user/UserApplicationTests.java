package com.github.hiling.user;

// import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
// @Slf4j

//基准测试类型：
// Throughput: 整体吞吐量，例如“1秒内可以执行多少次调用”；
// AverageTime: 调用的平均时间，例如“每次调用平均耗时xxx毫秒”；
// SampleTime: 随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”
// SingleShotTime: 以上模式都是默认一次iteration是1s，唯有SingleShotTime是只运行一次。往往同时把Warmup次数设为0，用于测试冷启动时的性能。
// All(“all”, “All benchmark modes”);
@BenchmarkMode(Mode.All)
//预热轮数
@Warmup(iterations = 3)
//度量，iterations 进行测试的轮次；time 每轮进行的时长；timeUnit 时长单位
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
//基准测试结果的时间类型。一般选择秒、毫秒、微秒。
@OutputTimeUnit(TimeUnit.MILLISECONDS)
//状态的共享范围，有三个值：Thread: 该状态为每个线程独享。Group: 该状态为同一个组里面所有线程共享。Benchmark: 该状态在所有线程间共享。
@State(Scope.Thread)
//每个进程中的测试线程。
@Threads(16)
//进行 fork 的次数。如果 fork 数是2的话，则 JMH 会 fork 出两个进程来进行测试。
@Fork(1)
public class UserApplicationTests {

    //对要被测试性能的代码添加注解，说明该方法是要被测试性能的
    @Benchmark
    public void createToken() {
        String url = "http://localhost:8000/token?grant_type=password&username=hiling&password=12345&client_id=vendor";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, null, String.class);
    }

    //score的结果是xxx ± xxx，单位是每毫秒多少个操作。
    @Test
    public void contextLoads() throws Exception{
        Options opt = new OptionsBuilder().include(UserApplicationTests.class.getSimpleName()).build();
        new Runner(opt).run();
    }}
