package com.polarbookshop.dispatcher_service.function;

import com.polarbookshop.dispatcher_service.message.OrderAcceptedMessage;
import com.polarbookshop.dispatcher_service.message.OrderDispatchedMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

@FunctionalSpringBootTest
public class DispatchingFunctionsIntegrationTests {
    @Autowired
    private FunctionCatalog catalog;

    @Test
    void packAndLabelOrder() {
        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> function = catalog.lookup(Function.class, "pack|label");

        long orderId = 121;
        StepVerifier.create(function.apply(new OrderAcceptedMessage(orderId)))
                .expectNextMatches(dispatchedOrder -> dispatchedOrder.equals(new OrderDispatchedMessage(orderId)))
                .verifyComplete();
    }
}
