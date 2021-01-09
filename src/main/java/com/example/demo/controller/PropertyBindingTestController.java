package com.example.demo.controller;

import com.example.demo.domain.Number;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PropertyBindingTestController {

    @GetMapping("/propertyTest1")
    public ResponseEntity<Object> propertyTest1(@RequestParam Number number) {
        return ResponseEntity.ok(number);
    }

    @PostMapping("/propertyTest2")
    public ResponseEntity<Object> propertyTest2(@RequestParam Number number) {
        return ResponseEntity.ok(number);
    }
}
