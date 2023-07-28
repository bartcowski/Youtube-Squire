package com.github.bartcowski;

import com.github.bartcowski.infrastructure.MainAppService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext("com.github.bartcowski")) {
            MainAppService service = context.getBean(MainAppService.class);
            service.startApp();
        }
    }
}
