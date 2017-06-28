package amqp;

import lombok.extern.apachecommons.CommonsLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@CommonsLog
@Component
@EnableScheduling
public class Producer {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Scheduled(fixedDelay = 3000L)
  public void send() {
    log.debug("send msg start");
    for (int i = 0; i < 10; i++) {
      rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME_1, "test.rabbit" + i);
      rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME_2, "test.turtle" + i);
    }
    log.debug("send msg end");
  }

}
