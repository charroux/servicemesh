package com.charroux.carservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarServiceController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

}
