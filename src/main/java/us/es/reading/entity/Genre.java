package us.es.reading.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Genre {

    private String genre;

    private String title;

    private String description;

    private List<Book> books;
}
