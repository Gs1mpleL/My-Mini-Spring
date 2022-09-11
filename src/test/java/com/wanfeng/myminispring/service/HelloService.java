package com.wanfeng.myminispring.service;

import com.wanfeng.myminispring.Bean.Car;
import com.wanfeng.myminispring.beans.factory.annotation.Autowired;
import com.wanfeng.myminispring.beans.factory.annotation.Value;
import com.wanfeng.myminispring.stereotype.Component;

@Component("service")
public class HelloService implements IService{
    @Value("${carName}")
    private String carName;

    @Autowired
    private Car car;
    @Override
    public void sayHello(){
        System.out.println("Hello,Spring" + carName +"注入了一个Car" + car.getCarName());
    }
}
