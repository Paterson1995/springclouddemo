package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @auther zzyy
 * @create 2020-02-20 11:11
 */
@Service
public class PaymentService
{
    /**
     * 正常访问，肯定OK
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id)
    {
        return "线程池:  "+Thread.currentThread().getName()+"  paymentInfo_OK,id:  "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    //运行出现异常，或者超过设置的请求响应时间3秒钟会自动调用该注解fallbackMethod指定的方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    })
    public String paymentInfo_TimeOut(Integer id)
    {
        //int age = 10/0;
        int seconds = 3;
        try { TimeUnit.SECONDS.sleep(seconds); } catch (InterruptedException e) { e.printStackTrace(); }
        return "线程池:  "+Thread.currentThread().getName()+" id:  "+id+"\t"+"O(∩_∩)O哈哈~"+"  耗时(秒): "+seconds;
    }
    public String paymentInfo_TimeOutHandler(Integer id)
    {
        return "线程池:  "+Thread.currentThread().getName()+"  8001系统繁忙或者运行报错，请稍后再试,id:  "+id+"\t"+"o(╥﹏╥)o";
    }

    //=====服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            /**
             *     static final Integer default_metricsRollingStatisticalWindow = 10000;
             *     private static final Integer default_circuitBreakerRequestVolumeThreshold = 20;
             *     private static final Integer default_circuitBreakerSleepWindowInMilliseconds = 5000;
             *     private static final Integer default_circuitBreakerErrorThresholdPercentage = 50;
             */
            //断路器熔断的最小请求数。例如默认值20的时候，如果滚动时间窗（默认为最近的10秒）内仅收到19个请求，即使这19个请求都失败了，断路器也不会打开。
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            //用来设置当断路器打开之后的休眠时间窗。休眠时间窗结束之后,会将断路器设置为“半开”状态，尝试接受下一次熔断的请求，如果还是失败断路器依然为打开状态，如果成功，就设置为关闭状态。
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "70"),// 失败率达到多少断路器打开，默认为50
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        if(id < 0)
        {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }

}
