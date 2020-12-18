package com.create.di.parser.impl;

import com.create.di.parser.BeanConfigParser;
import com.create.di.parser.BeanDefinition;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class XmlBeanConfigParser implements BeanConfigParser {

    @Override
    public List<BeanDefinition> parse(InputStream inputStream) {
        List<BeanDefinition> beanDefinitionList = new ArrayList<>();
        try {
            Document document = new SAXReader().read(inputStream);
            Element beans = document.getRootElement();
            Element bean = beans.element("bean");
            Attribute id = bean.attribute("id");
            Attribute path = bean.attribute("class");

            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setName(id.getText());
            beanDefinition.setClassName(path.getText());

            List<BeanDefinition.ConstructorArg> constructorArgs = new ArrayList<>();
            List<Element> attributes = bean.elements();

            for (Element element : attributes) {
                Attribute value = element.attribute("value");
                Attribute ref = element.attribute("ref");

                BeanDefinition.ConstructorArg constructorArg = new BeanDefinition.ConstructorArg();
                constructorArg.setIfRef(false);
                constructorArg.setType(ref.getText().getClass());
                constructorArg.setArg(value.getText());
                constructorArgs.add(constructorArg);
            }
            beanDefinition.setConstructorArgs(constructorArgs);
            beanDefinitionList.add(beanDefinition);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return beanDefinitionList;
    }

    @Override
    public List<BeanDefinition> parse(String configContent) {
        List beanDefinitions = new ArrayList<>(); // TODO:... return beanDefinitions;
        return beanDefinitions;
    }
}
