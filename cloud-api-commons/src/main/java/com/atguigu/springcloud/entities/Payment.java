package com.atguigu.springcloud.entities;

import java.io.Serializable;

public class Payment implements Serializable {

    private Long id;
    private String serial;

    public Payment(long id, String serial) {
        id = id;
        serial = serial;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", serial='" + serial + '\'' +
                '}';
    }
}
