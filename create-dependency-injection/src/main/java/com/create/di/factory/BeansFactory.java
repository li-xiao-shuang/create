package com.create.di.factory;

import com.create.di.exception.BeanCreationFailureException;
import com.create.di.exception.NoSuchBeanDefinitionException;
import com.create.di.parser.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class BeansFactory {
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitionList) {
        for (BeanDefinition beanDefinition : beanDefinitionList) {
            this.beanDefinitions.putIfAbsent(beanDefinition.getName(), beanDefinition);
        }

        for (BeanDefinition beanDefinition : beanDefinitionList) {
            if (beanDefinition.isLazyInit() == false && beanDefinition.isSingleton()) {
                createBean(beanDefinition);
            }
        }
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitions.get(beanName);
        if (beanDefinition == null) {
            throw new NoSuchBeanDefinitionException("Bean is not defined: " + beanName);
        }
        return createBean(beanDefinition);
    }

    protected Object createBean(BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton() && singletonObjects.contains(beanDefinition.getName())) {
            return singletonObjects.get(beanDefinition.getName());
        }

        // TODO: 2020/12/18 通过反射加载类、类加载器加载、字节码加载
        Object bean = null;
        try {
            Class beanClass = Class.forName(beanDefinition.getClassName());
            List<BeanDefinition.ConstructorArg> args = beanDefinition.getConstructorArgs();
            if (args == null) {
                bean = beanClass.getDeclaredConstructor().newInstance();
            } else {
                Class[] parameterTypes = new Class[args.size()];
                Object[] argObjects = new Object[args.size()];
                
                for (int i = 0; i < args.size(); ++i) {
                    BeanDefinition.ConstructorArg arg = args.get(i);
                    if (!arg.isIfRef()) {
                        parameterTypes[i] = arg.getType();
                        argObjects[i] = arg.getArg();
                    } else {
                        // TODO: 2020/12/18 注入是类对象引用的实现
//                        BeanDefinition refBeanDefinition = beanDefinitions.get(arg.getArg());
//                        if (refBeanDefinition == null) {
//                            throw new NoSuchBeanDefinitionException("Bean is not defined: " + arg.getArg());
//                        }
//                        argClasses[i] = Class.forName(refBeanDefinition.getClassName());
//                        argObjects[i] = createBean(refBeanDefinition);
                    }
                }
                bean = beanClass.getConstructor(parameterTypes).newInstance(argObjects);
            }
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new BeanCreationFailureException("", e);
        }

        if (bean != null && beanDefinition.isSingleton()) {
            singletonObjects.putIfAbsent(beanDefinition.getName(), bean);
            return singletonObjects.get(beanDefinition.getName());
        }
        return bean;
    }
}