package com.github.seastar.wdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class WdmApplication {
    public static void main(String[] args) {
        SpringApplication.run(WdmApplication.class, args);
    }
}