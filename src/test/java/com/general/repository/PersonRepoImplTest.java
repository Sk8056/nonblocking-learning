package com.general.repository;

import com.general.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Predicate;

class PersonRepoImplTest {

    private final Logger logger = LoggerFactory.getLogger(PersonRepoImplTest.class);

    PersonRepoImpl personRepo;

    @BeforeEach
    void setUp() {
        logger.info("Running BeforeEach...");
        personRepo = new PersonRepoImpl();
    }

    @Test
    @DisplayName("Running getById")
    void getById() {
        Mono<Person> personMono = personRepo.getById(1);
        Person person = personMono.block();
        logger.info("person {} ", person);
    }

    @Test
    @DisplayName("Running getByIdSubscribe")
    void getByIdSubscribe() {
        Mono<Person> personMono = personRepo.getById(1);

        StepVerifier.create(personMono)
                .expectNextCount(1)
                .verifyComplete();

        personMono.subscribe(p -> logger.info("person {} ", p));
    }

    @Test
    @DisplayName("Running getByIdMapFunction")
    void getByIdMapFunction() {
        Mono<Person> personMono = personRepo.getById(1);
        personMono.map(Person::getFirstName)
                .subscribe(firstName -> logger.info("person {} ", firstName));
    }

    @Test
    @DisplayName("Running fluxTestBlock")
    void fluxTestBlock() {
        Flux<Person> personFlux = personRepo.findAll();
        Person person = personFlux.blockFirst();
        logger.info("person {} ", person);
    }

    @Test
    @DisplayName("Running testFluxSubscribe")
    void testFluxSubscribe() {
        Flux<Person> personFlux = personRepo.findAll();
        StepVerifier.create(personFlux).expectNextCount(4).verifyComplete();
        personFlux
                .concatWith(Flux.error(new Exception("Just testing")))
                .onErrorReturn(Person.builder().id(0).build())
                .subscribe(p -> logger.info("person {} ", p));
    }

    @Test
    @DisplayName("Running testFluxToListMono")
    void testFluxToListMono() {
        Flux<Person> personFlux = personRepo.findAll();
        Mono<List<Person>> personListMono = personFlux.collectList();
        personListMono.subscribe(list ->  list.forEach(person -> logger.info("person {} ", person)));
    }

    /**
     * next method finds the first matched item if exist else gives empty mono
     */
    @Test
    @DisplayName("Running testPersonById")
    void testPersonById() {
        Flux<Person> personFlux = personRepo.findAll();
        Predicate<Person> personPredicate = person -> person.getId() == 1;
        Mono<Person> personMono = personFlux.filter(personPredicate).next();
        personMono.subscribe(p -> logger.info("person {} ", p));
    }

    /**
     * single is different from next, it gives exactly one item else throws specific exceptions
     * do* method won't change the pipeline they are there as a side effect
     * onErrorReturn will send the item in onNext rather in onError method.
     */
    @Test
    @DisplayName("Running testPersonByIdWithExceptions")
    void testPersonByIdWithExceptions() {
        Flux<Person> personFlux = personRepo.findAll();
        Predicate<Person> personPredicate = person -> person.getId() == 0;
        Mono<Person> personMono = personFlux.filter(personPredicate).single();
        personMono
                .doOnError(throwable -> logger.error(throwable.getMessage()))
//                .onErrorMap(throwable -> new Exception("Just testing the subscriber error side"))
                .onErrorReturn(Person.builder().id(0).build())
                .subscribe(p -> logger.info("person {} ", p),
                        throwable -> logger.info("inside the subscriber error side"),
                        () -> logger.info("Data processing finished"));
    }
}