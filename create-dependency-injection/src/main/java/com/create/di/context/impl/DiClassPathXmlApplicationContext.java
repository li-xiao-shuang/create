package com.create.di.context.impl;

import com.create.di.context.DiApplicationContext;
import com.create.di.factory.BeansFactory;
import com.create.di.parser.BeanConfigParser;
import com.create.di.parser.BeanDefinition;
import com.create.di.parser.impl.XmlBeanConfigParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DiClassPathXmlApplicationContext implements DiApplicationContext {

    private BeansFactory beansFactory;
    private BeanConfigParser beanConfigParser;

    public DiClassPathXmlApplicationContext(String configLocation) {
        this.beansFactory = new BeansFactory();
        this.beanConfigParser = new XmlBeanConfigParser();
        loadBeanDefinitions(configLocation);
    }

    private void loadBeanDefinitions(String configLocation) {
        InputStream in = null;
        try {
            in = this.getClass().getResourceAsStream("/" + configLocation);
            if (null == in) {
                throw new RuntimeException("Can not find config file: " + configLocation);
            }
            List<BeanDefinition> beanDefinitions = beanConfigParser.parse(in);
            beansFactory.addBeanDefinitions(beanDefinitions);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) { // TODO: log error
                }
            }
        }
    }

    @Override
    public Object getBean(String beanName) {
        return beansFactory.getBean(beanName);
    }
}
