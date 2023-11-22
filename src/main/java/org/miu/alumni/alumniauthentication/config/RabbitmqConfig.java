package org.miu.alumni.alumniauthentication.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        return container;
    }

    @Bean
    public Queue userRemoveQueue() {
        return new Queue("q-user-remove", true);
    }

    @Bean
    FanoutExchange userRemoveFanoutExchange() {
        return new FanoutExchange("user-fanout-exchange");
    }

    @Bean
    Binding userRemoveQueueBinding(Queue userRemoveQueue, FanoutExchange userRemoveFanoutExchange) {
        return BindingBuilder.bind(userRemoveQueue).to(userRemoveFanoutExchange);
    }


}
