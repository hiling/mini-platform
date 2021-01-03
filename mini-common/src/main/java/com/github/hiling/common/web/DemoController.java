package com.github.hiling.common.web;

// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author hiling
 * @date 2019/10/30 16:33
 */
@Configuration
@RequestMapping("/common/demo")
public class DemoController {

    @GetMapping
    @ResponseBody
    public ResponseEntity<String> error(HttpServletRequest request) {
        return new ResponseEntity<>("Demo:"+ LocalDateTime.now(), HttpStatus.OK);
    }

}
