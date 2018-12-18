package com.thehecklers.psedgeservice;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by markheckler on 6/7/16.
 */
@RestController
public class ReadingController {
    private Source source;

    public ReadingController(Source source) {
        this.source = source;
    }

    @GetMapping("/lastreading")
    public Reading getLastReading() {
        return DataHandler.lastReading;
    }

    @GetMapping("/dummyreading")
    public Reading getDummyReading() {
        return new Reading(1,
                0,
                800.0,
                32.0,
                12.0,
                1.2,
                1);
    }

    @PostMapping("/reading")
    public void postReading(@RequestBody Reading reading) {
        System.out.println(">> Reading posted to RMQ: |" + reading.toString() + "|");
        source.output().send(MessageBuilder.withPayload(reading).build());
    }

    @PostMapping("/test")
    public void postTest(@RequestBody String testString) {
        System.out.println(">>>>> Posted: |" + testString + "|");
    }
}
