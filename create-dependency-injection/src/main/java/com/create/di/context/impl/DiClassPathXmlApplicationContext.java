package com.create.di.context.impl;

import com.create.di.context.DiApplicationContext;
import com.create.di.factory.BeansFactory;
import com.create.di.parser.BeanConfigParser;

public class DiClassPathXmlApplicationContext implements DiApplicationContext {

    private BeansFactory beansFactory;
    private BeanConfigParser beanConfigParser;

    public Object getBean(String beanName) {
        return null;
    }
}
