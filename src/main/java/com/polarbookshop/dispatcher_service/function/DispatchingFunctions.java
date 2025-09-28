package com.polarbookshop.dispatcher_service.function;

import com.polarbookshop.dispatcher_service.message.OrderAcceptedMessage;
import com.polarbookshop.dispatcher_service.message.OrderDispatchedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class DispatchingFunctions {
    private static final Logger log = LoggerFactory.getLogger(DispatchingFunctions.class);

    @Bean
    public Function<OrderAcceptedMessage, Long> pack() {
        return orderAcceptedMessage -> {
            log.info("Packing order with ID: {}", orderAcceptedMessage.orderId());
            // Implement packing logic here
            return orderAcceptedMessage.orderId();
        };
    }

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
        return orderFlux -> orderFlux.map(orderId -> {
            log.info("Labeling order with ID: {}", orderId);
            // Implement labeling logic here
            return new OrderDispatchedMessage(orderId);
        });
    }
}
