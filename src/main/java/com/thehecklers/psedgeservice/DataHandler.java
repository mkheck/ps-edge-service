package com.thehecklers.psedgeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by markheckler on 9/25/15.
 */
@Service
public class DataHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessionList = new ArrayList<>();

//    MAH: Replace with SCSt+RabbitMQ handoff
//    @Autowired
//    private ReadingRepository repo;

    public static Reading lastReading;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        System.out.println("Data connection established from " + session.toString() + ", TIME: " + new Date().toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Reading reading = mapper.readValue(message.getPayload(), Reading.class);

            //repo.save(reading);
            System.out.println("New reading: " + reading.toString());

            lastReading.setReading(reading);

            sessionList.stream().filter(s -> s != session).forEach(s -> {
                try {
                    s.sendMessage(message);
                } catch (IOException e) {
                    System.out.println("Exception re-sending data message: " + e.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        System.out.println("Data connection closed by " + session.toString() + ", TIME: " + new Date().toString());
    }
}
