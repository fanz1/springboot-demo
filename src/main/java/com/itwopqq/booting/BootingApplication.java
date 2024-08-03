package com.itwopqq.booting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.itwopqq.booting.*.mapper")
@EnableAspectJAutoProxy
public class BootingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootingApplication.class, args);
    }

}
