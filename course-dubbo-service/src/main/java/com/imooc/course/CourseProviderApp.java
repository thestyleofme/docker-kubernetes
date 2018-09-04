package com.imooc.course;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.imooc.course")
public class CourseProviderApp {
    public static void main(String[] args) {
//        SpringApplication.run(CourseProviderApp.class, args);
        new SpringApplicationBuilder(CourseProviderApp.class)
                .web(false) // 非 Web 应用
                .run(args);
    }

}
