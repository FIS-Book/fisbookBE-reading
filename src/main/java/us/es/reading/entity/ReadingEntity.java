package us.es.reading.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(value = "readings")
@Getter
@Setter
public class ReadingEntity {

    @Id
    private ObjectId id;

    private String userId;

    List<Genre> genres;
}
