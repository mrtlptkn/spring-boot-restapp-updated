package com.mertalptekin.springbootrestapp._demo.springContext.custom;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // Her web isteği için yeni bir bean instance'ı oluşturur
public class WebRequestBasedBean {

    // Registrar.
    private final ApplicationContext applicationContext;

    public WebRequestBasedBean(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        System.out.println("WebRequestBasedBean initialized: " + this);
    }

    // AppplicationContext üzerinden başka bean'lere erişebiliriz
    // Service Locator yaklaşımı.
    public  void  test(){
        System.out.println("WebRequestBasedBean instance: " + this);
        MyCustomBean mb = this.applicationContext.getBean(MyCustomBean.class);
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("WebRequestBasedBean about to be destroyed: " + this);
    }

}
