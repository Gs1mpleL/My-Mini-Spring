package com.wanfeng.myminispring.Bean;

import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.context.ApplicationContext;
import com.wanfeng.myminispring.context.ApplicationContextAware;
import lombok.Data;

@Data
public class Car implements ApplicationContextAware {
    private String carName;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("感知到的ac"+applicationContext);
    }
}
