package com.github.hiling.item;

import com.github.hiling.common.web.DemoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author hiling
 */
@SpringBootApplication
@Import(value = {DemoController.class})
public class ItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class, args);
    }
}
