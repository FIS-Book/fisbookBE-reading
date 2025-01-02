package us.es.reading.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("${readings.base.url}/healthz")
public class HealthzController {

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("200");
    }
}