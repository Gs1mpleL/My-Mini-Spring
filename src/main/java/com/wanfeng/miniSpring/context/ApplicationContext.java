package com.wanfeng.miniSpring.context;

import com.wanfeng.miniSpring.beans.factory.HierarchicalBeanFactory;
import com.wanfeng.miniSpring.beans.factory.ListableBeanFactory;
import com.wanfeng.miniSpring.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}
