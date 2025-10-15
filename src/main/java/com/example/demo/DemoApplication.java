package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication // @Configuration + @ComponentScan + @EnableAutoConfiguration
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    // Once connect run the project, open http://localhost:8080/hello
    @RestController
    static class HelloController {
        @GetMapping("/hello")
        public String hello() { return "Hello, Spring Boot! This is an awesome demo"; }
    }
}

