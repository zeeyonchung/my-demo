package com.example.demo.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class LogTestController {

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

    @GetMapping("/logTest1")
    public ResponseEntity<String> logTest1(@RequestParam String name) {
        log.info("logTest1... {}", name);

        return ResponseEntity.ok("{\"result\": \"OK!\"}");
    }

    @PostMapping("/logTest2")
    public ResponseEntity<String> logTest2(@RequestParam String name) {
        log.info("logTest2... {}", name);

        return ResponseEntity.ok("OK!");
    }

    @PostMapping("/logTest3")
    public ResponseEntity<TestMember> logTest3(@RequestBody TestMember member) {
        log.info("logTest3...");

        return ResponseEntity.ok(member);
    }

    @PostMapping("/logTest4")
    public ResponseEntity<TestHobby> logTest4(@ModelAttribute TestHobby hobby) {
        log.info("logTest4...");

        return ResponseEntity.ok(hobby);
    }

    @Getter
    public static class TestMember {
        private String name;
        private int age;
        private List<TestHobby> hobbies;
    }

    @Getter
    @Setter
    public static class TestHobby {
        private String name;

        // 아래 어노테이션은 form 요청값을 매핑하기 위한 설정이다.
        // json 매핑은 JacksonConfiguration.objectMapper에서 설정하였다.
        @DateTimeFormat(pattern = "yyyy.MM.dd")
        private LocalDate since;
    }
}
