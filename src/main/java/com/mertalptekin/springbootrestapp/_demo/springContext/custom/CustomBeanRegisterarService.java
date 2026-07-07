package com.mertalptekin.springbootrestapp._demo.springContext.custom;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanRegisterarService implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // Manuel Bean Registration işlemleri burada yapılabilir
        System.out.println("CustomBeanRegisterarService - postProcessBeanDefinitionRegistry called");
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyCustomBean.class);
        registry.registerBeanDefinition("customBean", beanDefinitionBuilder.getBeanDefinition());

        // SpringContext ayağa kalkmadan önce istediğimiz bean'leri burada kaydedebiliriz. Ve Spring Context register ederiz.
    }
}
