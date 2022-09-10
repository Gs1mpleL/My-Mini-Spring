package com.wanfeng.myminiSpring.context;

import com.wanfeng.myminiSpring.beans.factory.HierarchicalBeanFactory;
import com.wanfeng.myminiSpring.beans.factory.ListableBeanFactory;
import com.wanfeng.myminiSpring.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}
