package amqp;

import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@CommonsLog
@Component
@Getter
public class Consumer {

  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_1)
  public void receiveQ1R1(String message) {
    log.debug("<Q1R1> Received <" + message + ">");
  }

  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_2)
  public void receiveQ2R2(String message) {
    log.debug("<Q2R2> Received <" + message + ">");
  }

  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_1)
  public void receiveQ1R3(String message) {
    log.debug("<Q1R3> Received <" + message + ">");
  }

  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_2)
  public void receiveQ2R3(String message) {
    log.debug("<Q2R3> Received <" + message + ">");
  }

}