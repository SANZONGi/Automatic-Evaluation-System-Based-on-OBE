package com.sanzong.obe.config;

import com.sanzong.obe.interceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author SANZONG
 */
@Slf4j
@Configuration
@PropertySource("classpath:application.yml")
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;


    /**
     * jwt拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(roleInterceptor).addPathPatterns("/**");
    }


    //资源访问
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String res = "file:/home/ubuntu/imgs/";
//        if (File.separator.equals("/")) {
////            System.out.println("linux");
//        } else {
//            res = "file:///"+LocalImgPath;
////            System.out.println("other");
//        }
//        registry.addResourceHandler("/static/**").addResourceLocations(res);
////        if (!registry.hasMappingForPattern("/**")) {
////            registry.addResourceHandler("/**").addResourceLocations(
////                    CLASSPATH_RESOURCE_LOCATIONS);
////        }
//
//    }
}