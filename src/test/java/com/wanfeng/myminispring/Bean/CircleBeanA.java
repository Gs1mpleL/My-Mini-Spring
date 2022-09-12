package com.wanfeng.myminispring.Bean;

import com.wanfeng.myminispring.beans.factory.annotation.Autowired;
import com.wanfeng.myminispring.stereotype.Component;

@Component
public class CircleBeanA {
    @Autowired
    private CircleBeanB circleBeanB;
}
