package com.whh.springboot.h2.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Map;

public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext ctx = null;

    public static Object getBean(String name) {
//        if (ctx == null) {
//            ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(ctx.getServletContext());
//        }
        return ctx.getBean(name);
    }

    public static Object getBean(String name, ServletContext context) {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        return ctx.getBean(name);
    }

    public static Object registerBean(Class<?> beanClass, String beanName) throws Exception {
        if (beanName == null || "".equals(beanName)) {
            beanName = beanClass.getName();
        }
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) ((ConfigurableApplicationContext) ctx).getBeanFactory();
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();
        factory.registerBeanDefinition(beanName, beanDefinition);
        return getBean(beanName);
    }

    public static Object registerBean(Class<?> beanClass) throws Exception {
        return registerBean(beanClass, null);
    }

    public static Object getBeanByClass(Class c) {
        return ctx.getBean(c);
    }

    public static Map getBeansByClass(Class c) {
        return ctx.getBeansOfType(c);
    }

    public static ApplicationContext getCtx() {
        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (null == ctx) {
            ctx = arg0;
        }
    }
}
