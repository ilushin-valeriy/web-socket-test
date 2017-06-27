package ru.ilushin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.*;

@Service
public class MessageHandlerService {


  private static final Logger logger = LoggerFactory.getLogger(MessageHandlerService.class);

  private static class MyMessage implements Runnable {

    private final WebSocketSession session;
    private final TextMessage message;


    public MyMessage(WebSocketSession session, TextMessage message) {
      this.session = session;
      this.message = message;
    }


    @Override
    public void run() {
      logger.info(String.format("start handle message.\n session:%s\n message:%s", session.toString(), message.toString()));

      try {
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
        TextMessage returnMessage = new TextMessage(message.getPayload()
                + " received at server." + " internal data: {session = " + session.toString() + ", message = " + message.toString() + "}");
        logger.info("Return Message: " + returnMessage.getPayload());

        session.sendMessage(returnMessage);

      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private BlockingQueue<Runnable> messagesQueue;
  private ThreadPoolExecutor executor;

  public MessageHandlerService() {
    messagesQueue = new LinkedBlockingQueue<>();
    executor = new ThreadPoolExecutor(2, 8, 10L, TimeUnit.SECONDS, messagesQueue);

    logger.info("MessageHandlerService created");
  }

  public void popHandleRequest(WebSocketSession session, TextMessage message) {
    executor.execute(new MyMessage(session, message));
    logger.info("message planed. "+message.toString());
  }

}
