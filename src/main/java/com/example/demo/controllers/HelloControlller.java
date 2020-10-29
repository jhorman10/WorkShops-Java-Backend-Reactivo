package com.example.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/hello")
public class HelloControlller {

    private String sayBlockingHello() {
        try {
            Thread.sleep(2000);
            return "<h1>Hello!</h1>";
        } catch (InterruptedException e) {
            return null;
        }
    }

    @GetMapping
    public Mono<String> sayHello(){
        return Mono.fromSupplier(() -> sayBlockingHello())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(
            path = "/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<ServerSentEvent<Long>> sseInteger(){
        return Flux.interval(Duration.of(200, ChronoUnit.MILLIS))
                .take(20)
                .map(aLong -> ServerSentEvent.<Long>builder()
                        .data(aLong)
                        .build());
        // return  null;
    }
}
