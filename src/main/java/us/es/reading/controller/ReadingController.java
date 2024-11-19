package us.es.reading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import us.es.reading.entity.ReadingEntity;
import us.es.reading.repository.ReadingRepository;

@RestController
@RequestMapping("/api/readings")
public class ReadingController {

 @Autowired
    private ReadingRepository readingRepository;

    @GetMapping
    public ResponseEntity<List<ReadingEntity>> getReadings(){
        return new ResponseEntity<>(readingRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReading(@RequestBody ReadingEntity entity){
        readingRepository.save(entity);
    }

}
