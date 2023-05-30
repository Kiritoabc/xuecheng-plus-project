package com.boluo.system;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 *     系统管理启动类
 * </p>
 *
 * @Description:
 */
@EnableScheduling
@EnableSwagger2Doc
@SpringBootApplication
@MapperScan("com.boluo.system.mapper")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}