package com.wegoo.apidoc;

import com.terran4j.commons.api2doc.config.EnableApi2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableApi2Doc
public class Api2DocApplication {

    public static void main(String[] args) {
        SpringApplication.run(Api2DocApplication.class, args);
    }
}
