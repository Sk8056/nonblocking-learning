package com.general.repository;

import com.general.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepo {

    Mono<Person> getById(Integer id);

    Flux<Person> findAll();
}
