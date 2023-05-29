package com.boluo;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kirit
 * @version 1.0
 * @description: 内容管理服务启动类
 * @date 2023/5/27 19:06
 */

@SpringBootApplication
@EnableSwagger2Doc
@MapperScan("com.boluo.content.mapper")
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class,args);
    }
}
