package us.es.reading.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.es.reading.config.ConstantsConfig;
@RestController
@RequestMapping(ConstantsConfig.API_BASE_URL+"/healthz")
public class HealthzController {

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("200");
    }
}