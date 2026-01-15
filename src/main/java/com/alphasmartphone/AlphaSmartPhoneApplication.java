package com.alphasmartphone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AlphaSmartPhoneApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlphaSmartPhoneApplication.class, args);
    }
}
