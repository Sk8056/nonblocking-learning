package com.general.repository;

import com.general.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepoImpl implements PersonRepo {

    Person michael = new Person(3, "Michale", "Weston");
    Person fiona = new Person(2, "Fiano", "Weston");
    Person sam = new Person(3, "Sam", "Axe");
    Person jesse = new Person(3, "Jesse", "Porter");

    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(michael);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, fiona, sam, jesse);
    }
}
