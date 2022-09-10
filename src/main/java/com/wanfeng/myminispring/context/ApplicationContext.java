package com.wanfeng.myminispring.context;

import com.wanfeng.myminispring.beans.factory.HierarchicalBeanFactory;
import com.wanfeng.myminispring.beans.factory.ListableBeanFactory;
import com.wanfeng.myminispring.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}
