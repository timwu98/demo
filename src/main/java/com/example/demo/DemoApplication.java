package com.example.demo;

import com.example.demo.model.Person;
import com.example.demo.model.CalculationRequest;
import com.example.demo.model.ExpressionRequest;
import com.example.demo.utils.ExprCalculate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.text.DecimalFormat;

@SpringBootApplication // @Configuration + @ComponentScan + @EnableAutoConfiguration
public class DemoApplication {
    public static void main(String[] args) {
        System.out.println("Running with Java " + System.getProperty("java.version"));
        SpringApplication.run(DemoApplication.class, args);
    }


    // Once connect run the project, open http://localhost:8080/hello
    @RestController
    static class HelloController {
        @GetMapping("/hello")
        public String sayHello() { return "Hello, Spring Boot! This is an awesome demo"; }

        @GetMapping("/echo")
        public String echoGet(@RequestParam String name, @RequestParam String message) {
            return "You sent: " + name + ", " + message;
        }

        @PostMapping("/echo")
        public String echo(@RequestBody Person person) {
//            String message = "Name: " + person.getLastName() + " " + person.getFirstName() +", age " + person.getAge();
            return "Person Name: %s %s, age: %s".formatted(
                    person.getLastName(),
                    person.getFirstName(),
                    person.getAge()
            );
        }
        @PostMapping("/cal")
        public String calculate(@RequestBody CalculationRequest cal) {
            double num1 = cal.getNum1();
            double num2 = cal.getNum2();
            String operate = cal.getOperate();
            DecimalFormat df = new DecimalFormat("0.00");
            String result = switch (operate) {
                case "+" -> df.format(num1 + num2);
                case "-" -> df.format(num1 - num2);
                case "*" -> df.format(num1 * num2);
                case "/" -> {
                    if (num2 == 0) {
                        throw new IllegalArgumentException("Cannot divide by zero");
                    }
                    yield df.format(num1 / num2);
                }
                default -> throw new IllegalArgumentException("Unsupported operator");
            };

            return "calculate: %s %s %s = %s".formatted(
                    num1, operate, num2, result
            );
        }
        @PostMapping("/cal/expr")
        public String exprCalculate(@RequestBody ExpressionRequest req) {
            if (req.getExpr() == null || req.getExpr().isBlank())
                throw new IllegalArgumentException("expr is required");
            long value = ExprCalculate.calculate(req.getExpr());
            return "exprCalculate: %s = %s".formatted(
                    req.getExpr(), value
            );
        }
    }
}

