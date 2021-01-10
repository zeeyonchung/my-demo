package com.example.demo.controller;

import com.example.demo.domain.Number;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PropertyBindingTestController {

    // 프로젝트 외부 파일 가져오기
    // maven 명령어: mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.config.location=C:/Users/zeeyo/projects/properties/application-test.properties
    // java 명령어: java -jar -Dspring.config.location=classpath:/application.properties,C:/Users/zeeyo/projects/properties/application-test.properties target/demo-0.0.1-SNAPSHOT.jar
    // profile-specific properties과 함께 쓰기: java -jar -Dspring.config.additional-location=C:/Users/zeeyo/projects/properties/application-test.properties target/demo-0.0.1-SNAPSHOT.jar
    @Value("${test.external.property.key:default}")
    private String externalProperty;

    @Value("${application.properties.key}")
    private String internalSharedProperty;

    @Value("${application.properties.env.key}")
    private String internalEnvProperty;

    @GetMapping("/propertyTest1")
    public ResponseEntity<Object> propertyTest1(@RequestParam Number number) {
        return ResponseEntity.ok(number);
    }

    @PostMapping("/propertyTest2")
    public ResponseEntity<Object> propertyTest2(@RequestParam Number number) {
        return ResponseEntity.ok(number);
    }

    @GetMapping("/propertyTest3")
    public ResponseEntity<Object> propertyTest3() {
        return ResponseEntity.ok(
                String.format("external:%s, internal-shared:%s, internal-env:%s",
                        externalProperty, internalSharedProperty, internalEnvProperty));
    }
}
