package com.code.classsystem;

import com.code.classsystem.util.ApplicationContextInfoUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages="com.code")
@MapperScan("com.code.classsystem.dao")
@EnableTransactionManagement
public class ClassSystemApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ClassSystemApplication.class, args);
        //打印系统访问信息
        ApplicationContextInfoUtils.printSystemInfo(applicationContext);
    }

}
