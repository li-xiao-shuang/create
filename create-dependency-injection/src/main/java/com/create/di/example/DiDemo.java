package com.create.di.example;

import com.create.di.context.DiApplicationContext;
import com.create.di.context.impl.DiClassPathXmlApplicationContext;

public class DiDemo {
    public static void main(String[] args) {
        DiApplicationContext diApplicationContext = new DiClassPathXmlApplicationContext("beans.xml");
        Config config = (Config) diApplicationContext.getBean("config");
        System.out.println("ip:" + config.getIp());
        System.out.println("userName:" + config.getUserName());
        System.out.println("password:" + config.getPassword());
    }
}
