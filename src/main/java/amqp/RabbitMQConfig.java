package amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@EnableRabbit
@ComponentScan("amqp")
@PropertySource("application.properties")
public class RabbitMQConfig {

  @Autowired
  private Environment env;

  public static final String QUEUE_NAME_1 = "1Q";
  public static final String QUEUE_NAME_2 = "2Q";

  public static final String DIRECT_EXCHANGE = "direct-exchange";
  public static final String TOPIC_EXCHANGE = "topic-exchange";
  public static final String FANOUT_EXCHANGE = "fanout-exchange";

  public static final String ROUTING_KEY1 = "*.rabbit";
  public static final String ROUTING_KEY2 = "*.turtle";
  public static final String ROUTING_KEY3 = "test.#";

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory factory = new CachingConnectionFactory();
    factory.setHost(env.getProperty("spring.rabbitmq.host"));
    factory.setPort(Integer.parseInt(env.getProperty("spring.rabbitmq.port")));
    factory.setUsername(env.getProperty("spring.rabbitmq.username"));
    factory.setPassword(env.getProperty("spring.rabbitmq.password"));
    return factory;
  }

  @Bean
  public RabbitTemplate rabbitTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    rabbitTemplate.setExchange(TOPIC_EXCHANGE);
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public Queue queue1() {
    return new Queue(QUEUE_NAME_1, false);
  }

  @Bean
  public Queue queue2() {
    return new Queue(QUEUE_NAME_2, false);
  }

  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(DIRECT_EXCHANGE);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(TOPIC_EXCHANGE);
  }

  @Bean
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange(FANOUT_EXCHANGE);
  }

  @Bean
  public Binding bindingQ1R1(Queue queue1, TopicExchange exchange) {
    return BindingBuilder.bind(queue1).to(exchange).with(ROUTING_KEY1);
  }

  @Bean
  public Binding bindingQ2R2(Queue queue2, TopicExchange exchange) {
    return BindingBuilder.bind(queue2).to(exchange).with(ROUTING_KEY2);
  }

  @Bean
  public Binding bindingQ1R3(Queue queue1, TopicExchange exchange) {
    return BindingBuilder.bind(queue1).to(exchange).with(ROUTING_KEY3);
  }

  @Bean
  public Binding bindingQ2R3(Queue queue2, TopicExchange exchange) {
    return BindingBuilder.bind(queue2).to(exchange).with(ROUTING_KEY3);
  }

  @Bean
  public Binding bindingQ1F(Queue queue1, FanoutExchange exchange) {
    return BindingBuilder.bind(queue1).to(exchange);
  }

  @Bean
  public Binding bindingQ2F(Queue queue2, FanoutExchange exchange) {
    return BindingBuilder.bind(queue2).to(exchange);
  }

}
