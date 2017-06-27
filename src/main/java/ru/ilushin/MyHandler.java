package ru.ilushin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class MyHandler extends TextWebSocketHandler {
  private static final Logger logger = LoggerFactory.getLogger(MyHandler.class);

  @Autowired
  MessageHandlerService messageHandlerService;

  public MyHandler() {
    logger.info("MyHandler created");
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    logger.info("new session:" + session);
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    logger.info(String.format("new incomming message.\n session:%s\n message:%s", session.toString(), message.toString()));
    super.handleTextMessage(session, message);
    TextMessage returnMessage = new TextMessage("sync." + message.getPayload()
            + " received at server");
    logger.info("Return Message: " + returnMessage.getPayload());
    session.sendMessage(returnMessage);

    messageHandlerService.popHandleRequest(session,message);
  }

}