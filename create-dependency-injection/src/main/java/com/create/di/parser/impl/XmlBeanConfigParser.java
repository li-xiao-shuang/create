package com.create.di.parser.impl;

import com.create.di.parser.BeanConfigParser;
import com.create.di.parser.BeanDefinition;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlBeanConfigParser implements BeanConfigParser {
    @Override
    public List<BeanDefinition> parse(InputStream inputStream) {
        String content = null;
        return parse(content);
    }

    @Override
    public List<BeanDefinition> parse(String configContent) {
        List beanDefinitions = new ArrayList<>(); // TODO:... return beanDefinitions;
        return beanDefinitions;
    }
}
