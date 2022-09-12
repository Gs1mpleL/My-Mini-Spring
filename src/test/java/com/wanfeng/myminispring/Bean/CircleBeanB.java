package com.wanfeng.myminispring.Bean;

import com.wanfeng.myminispring.beans.factory.annotation.Autowired;
import com.wanfeng.myminispring.stereotype.Component;

@Component
public class CircleBeanB {
    @Autowired
    private CircleBeanA circleBeanA;
}
