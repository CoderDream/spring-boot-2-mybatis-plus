package com.coderdream.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class YourController {

    @GetMapping("/get_header")
    public Map<String, Object> getYourData(@RequestHeader("token") String token, String id) {
        Map<String, Object> data = new HashMap<>();
        data.put("accessToken", token);
        data.put("id", id);
        return data;
    }
}
