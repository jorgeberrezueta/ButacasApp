package me.jorgeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ButacasBackendMain {

    public static final Logger LOGGER = LoggerFactory.getLogger(ButacasBackendMain.class);

    public static void main(String[] args) {
        SpringApplication.run(ButacasBackendMain.class, args);
    }

}