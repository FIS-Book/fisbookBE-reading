package us.es.reading.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(value = "readings")
@Getter
@Setter
@NoArgsConstructor
public class ReadingEntity {

    @Id
    private String id;   
    
    private String title;

    private String description;

    private String userId;

    private Integer units;
}
