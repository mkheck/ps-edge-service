package com.thehecklers.psedgeservice;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PingMessage;
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
public class ControlHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        System.out.println("Control connection established from " + session.toString() + ", TIME: " + new Date().toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            System.out.println("Control message '" + message + "' from " + session.toString());

            for (WebSocketSession sessionInList : sessionList) {
                if (sessionInList != session) {
                    sessionInList.sendMessage(message);
                    System.out.println("--> Sending control message '" + message + "' to " + sessionInList.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception handling control message: " + e.getLocalizedMessage());
        }
    }

    @Scheduled(fixedRate = 60000)
    public void sendPing() {
        for (WebSocketSession sessionInList : sessionList) {
            try {
                sessionInList.sendMessage(new PingMessage());
            } catch (IOException e) {
                System.out.println("Exception sending Control WebSocket ping to session " + sessionInList.getId() + ": " + e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        System.out.println("Control connection closed by " + session.toString() + ", TIME: " + new Date().toString());
    }
}
