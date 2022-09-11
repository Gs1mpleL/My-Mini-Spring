package com.wanfeng.myminispring.Bean;

import com.wanfeng.myminispring.beans.BeansException;
import com.wanfeng.myminispring.beans.factory.annotation.Autowired;
import com.wanfeng.myminispring.beans.factory.annotation.Value;
import com.wanfeng.myminispring.context.ApplicationContext;
import com.wanfeng.myminispring.context.ApplicationContextAware;
import com.wanfeng.myminispring.stereotype.Component;
import lombok.Data;

@Data
@Component
public class Car implements ApplicationContextAware {
    @Value("${carName}")
    private String carName;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("感知到的ac"+applicationContext);
    }
}
