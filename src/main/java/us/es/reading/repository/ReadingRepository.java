package us.es.reading.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import us.es.reading.entity.ReadingEntity;

public interface ReadingRepository extends MongoRepository<ReadingEntity, String> {

    Optional<ReadingEntity> findByUserId(String userId);
}
