package prj;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @className: RabbitMQConfig
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/5/26 10:58
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue objectQueue() {
        return new Queue("buyRecordQueue");
    }

    @Bean
    TopicExchange myExchange() {
        return new TopicExchange("myExchange");
    }

    @Bean
    Binding bindingObjectQueue(Queue objectQueue, TopicExchange exchange) {
        return BindingBuilder.bind(objectQueue).to(exchange)
                .with("buyRecordQueue");
    }
}
