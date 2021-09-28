package com.general.repository;

import com.general.entity.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface StudentRepo extends ReactiveMongoRepository<Student, String> {
    Mono<Student> findByFirstName(final String firstName);
}
