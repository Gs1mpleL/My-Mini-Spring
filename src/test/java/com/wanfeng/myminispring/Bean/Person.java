package com.wanfeng.myminispring.Bean;

import com.wanfeng.myminispring.beans.factory.DisposableBean;
import com.wanfeng.myminispring.beans.factory.InitializingBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements InitializingBean, DisposableBean {
    private String name;
    private int age;
    private Car car;
    public void customInitMethod() {
        System.out.println("I was born in the method named customInitMethod");
    }

    public void customDestroyMethod() {
        System.out.println("I died in the method named customDestroyMethod");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("销毁方法执行");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet（）执行");
    }
}