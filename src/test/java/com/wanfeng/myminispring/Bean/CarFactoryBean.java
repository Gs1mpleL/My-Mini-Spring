package com.wanfeng.myminispring.Bean;

import com.wanfeng.myminispring.beans.factory.FactoryBean;
import lombok.Data;

@Data
public class CarFactoryBean implements FactoryBean<Car> {
    private String carName;
    @Override
    public Car getObject() throws Exception {
        Car car  = new Car();
        car.setCarName(carName);
        return car;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
