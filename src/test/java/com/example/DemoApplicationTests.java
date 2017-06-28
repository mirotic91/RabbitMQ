package com.example;

import amqp.RabbitMQConfig;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@CommonsLog
@RunWith(SpringRunner.class)
@Import(RabbitMQConfig.class)
@SpringBootTest
public class DemoApplicationTests {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  public void testConvertAndSend() {
    for (int i = 0; i < 10; i++) {
      rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, RabbitMQConfig.ROUTING_KEY1, "test.rabbit" + i);
      rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, RabbitMQConfig.ROUTING_KEY2, "test.turtle" + i);
      rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, RabbitMQConfig.ROUTING_KEY3, "test.test" + i);
    }
  }

  @Test
  public void testConvertAndReceive() {
    rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME_1, "test.rabbit");
    rabbitTemplate.receiveAndConvert(RabbitMQConfig.QUEUE_NAME_1);
  }

}
