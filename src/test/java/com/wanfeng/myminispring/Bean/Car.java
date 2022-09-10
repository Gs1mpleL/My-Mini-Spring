package com.wanfeng.myminispring.Bean;

import com.wanfeng.miniSpring.beans.BeansException;
import com.wanfeng.miniSpring.context.ApplicationContext;
import com.wanfeng.miniSpring.context.ApplicationContextAware;
import lombok.Data;

@Data
public class Car implements ApplicationContextAware {
    private String carName;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("感知到的ac"+applicationContext);
    }
}
