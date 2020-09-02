package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther zzyy
 * @create 2020-02-18 10:27
 */
//建议用@Mapper，不要用@Resposity，否则插入时可能会出问题
@Mapper
public interface PaymentDao
{
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
