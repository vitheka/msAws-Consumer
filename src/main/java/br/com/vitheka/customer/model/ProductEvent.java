package br.com.vitheka.customer.model;

import lombok.Builder;

@Builder
public record ProductEvent(Long customerId, String fullName, String username) {
}
