package com.example.demo.controllers;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TestFlux {
    public static void main(String[] args) throws InterruptedException {
        Flux<Long> interval = Flux.interval(Duration.of(200, ChronoUnit.MILLIS))
                .doOnNext(System.out::println)
                .take(20);

        Disposable subscribe = interval.subscribe();

        subscribe.dispose(); // cerrar petición

        //interval.blockLast();

        //interval.subscribe(
                //aLong -> System.out.println(aLong)
        //);

        //Thread.sleep(5000); //duerme el hilo
    }
}