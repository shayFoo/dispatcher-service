package com.polarbookshop.dispatcher_service.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.dispatcher_service.message.OrderAcceptedMessage;
import com.polarbookshop.dispatcher_service.message.OrderDispatchedMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class DispatchingFunctionsIntegrationTests {
    @Autowired
    private InputDestination input;
    @Autowired
    private OutputDestination output;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenOrderAcceptedThenDispatched() throws IOException {
        long orderId = 121;
        Message<OrderAcceptedMessage> inputMessage = MessageBuilder.withPayload(new OrderAcceptedMessage(orderId))
                .build();
        Message<OrderDispatchedMessage> expectedOutputMessage = MessageBuilder.withPayload(new OrderDispatchedMessage(orderId))
                .build();

        input.send(inputMessage);

        assertThat(objectMapper.readValue(output.receive(10000, "order-dispatched").getPayload(), OrderDispatchedMessage.class))
                .isEqualTo(expectedOutputMessage.getPayload());

    }
}
