package br.com.vitheka.customer.model;

import br.com.vitheka.customer.enums.EventType;
import lombok.Builder;

@Builder
public record Envelope(EventType eventType, String data) {
}
