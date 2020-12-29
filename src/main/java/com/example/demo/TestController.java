package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("OK!");
    }

    @GetMapping("/log")
    public ResponseEntity<String> log() {
        log.trace("trace...");
        log.debug("debug...");
        log.info("info...");
        log.warn("warn...");
        log.error("error...");

        return ResponseEntity.ok("OK!");
    }

    @GetMapping("/log/error")
    public ResponseEntity<String> logError() {
        throw new RuntimeException("runtime exception...");
    }
}
