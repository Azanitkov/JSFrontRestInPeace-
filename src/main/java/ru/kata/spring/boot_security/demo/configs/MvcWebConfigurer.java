package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class MvcWebConfigurer implements WebMvcConfigurer {
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry){
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
}
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("users/users");
        registry.addViewController("/user").setViewName("users/user");
    }
}
