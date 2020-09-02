package com.atguigu.springcloud;

import com.atguigu.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @auther zzyy
 * @create 2020-02-18 17:20
 */
//包括@ComponentScan，会自动扫描启动类同级包和子包下的类
@SpringBootApplication
@EnableEurekaClient
//使用自定义的负载均衡算法时加这个注解
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration= MySelfRule.class)
public class OrderMain80
{
    public static void main(String[] args) {
            SpringApplication.run(OrderMain80.class, args);
    }
}
