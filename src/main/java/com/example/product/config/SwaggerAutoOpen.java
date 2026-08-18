package com.example.product.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SwaggerAutoOpen {

    @EventListener(ApplicationReadyEvent.class)
    public void openSwagger() {
        String url = "http://localhost:8080/swagger-ui/index.html";
        String os = System.getProperty("os.name").toLowerCase();

        try {
            ProcessBuilder builder;
            if (os.contains("win")) {
                builder = new ProcessBuilder("cmd", "/c", "start", url);
            } else if (os.contains("mac")) {
                builder = new ProcessBuilder("open", url);
            } else {
                builder = new ProcessBuilder("xdg-open", url);
            }
            builder.start();
        } catch (Exception e) {
            System.out.println("Không thể mở Swagger tự động (" + e.getMessage() + "). " +
                    "Vui lòng mở tay: " + url);
        }
    }
}