package com.create.di.parser;

import com.create.di.enums.Scope;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BeanDefinition {
    private String id;
    private String name;
    private String className;
    private List<ConstructorArg> constructorArgs = new ArrayList<>();
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;

    public boolean isSingleton() {
        return scope.equals(Scope.SINGLETON);
    }

    @Data
    public static class ConstructorArg {
        private boolean ifRef;
        private Class type;
        private Object arg;
    }
}




