package prj.config;

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
 * @date: 2022/6/4 14:34
 */
@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue objectQueue () {
        return new Queue("buyRecordQueue");
    }

    @Bean
    TopicExchange myExchange() {
        return new TopicExchange("myExchange");
    }

    @Bean
    Binding bindingObjectQueue (TopicExchange exchange, Queue buyRecordQueue) {
        return BindingBuilder.bind(buyRecordQueue).to(exchange)
                .with("buyRecordQueue");
    }
}
