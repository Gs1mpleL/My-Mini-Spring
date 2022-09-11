package com.wanfeng.myminispring.service;

import com.wanfeng.myminispring.stereotype.Component;

@Component("service")
public class HelloService implements IService{
    @Override
    public void sayHello(){
        System.out.println("Hello,Spring");
    }
}
