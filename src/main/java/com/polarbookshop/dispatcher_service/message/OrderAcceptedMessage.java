package com.polarbookshop.dispatcher_service.message;

public record OrderAcceptedMessage(
        long orderId
) {
}
