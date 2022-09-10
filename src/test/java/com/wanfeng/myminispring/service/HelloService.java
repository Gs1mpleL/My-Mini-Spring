package com.wanfeng.myminispring.service;

public class HelloService implements IService{
    @Override
    public void sayHello(){
        System.out.println("Hello,Spring");
    }
}
