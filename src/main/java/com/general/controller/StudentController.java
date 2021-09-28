package com.general.controller;

import com.general.dto.Response;
import com.general.repository.StudentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
@Slf4j
public class StudentController {

    private final StudentRepo studentRepo;

    @GetMapping(value = "/{firstName}")
    public Publisher<Response> getStudentByFirstName(@PathVariable String firstName) {
        return studentRepo.findByFirstName(firstName)
                .flatMap(student -> Mono.just(new Response(student, 200)))
                .onErrorReturn(new Response("Unable to perform operation", 500))
                .defaultIfEmpty(new Response(firstName + " Student not found", 200));
    }

    @GetMapping(value = "/all")
    public Publisher<Response> getAllStudents() {
        return studentRepo.findAll().concatMap(student -> Flux.just(new Response(student, 200)));
    }
}
