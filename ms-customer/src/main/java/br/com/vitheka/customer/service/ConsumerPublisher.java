package br.com.vitheka.customer.service;

import br.com.vitheka.customer.entity.Customer;
import br.com.vitheka.customer.model.Envelope;
import br.com.vitheka.customer.model.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.vitheka.customer.enums.EventType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ConsumerPublisher {

    private AmazonSNS snsClient;
    private Topic productEventsTopic;
    private ObjectMapper objectMapper;

    public ConsumerPublisher(AmazonSNS snsClient,
                             @Qualifier("productEventsTopic") Topic productEventsTopic,
                             ObjectMapper objectMapper) {

        this.snsClient = snsClient;
        this.productEventsTopic = productEventsTopic;
        this.objectMapper = objectMapper;
    }

    public void publishConsumerEvent(Customer customerIn, EventType eventType, String username) {

        var productEvent = ProductEvent.builder()
                .customerId(customerIn.getCustomerId())
                .fullName(customerIn.getFirstName() + " " + customerIn.getLastName())
                .username(username)
                .build();

        try {
            var envelope = Envelope.builder()
                    .eventType(eventType)
                    .data(objectMapper.writeValueAsString(productEvent))
                    .build();

            snsClient.publish(
                    productEventsTopic.getTopicArn(),
                    objectMapper.writeValueAsString(envelope)
            );

        } catch (JsonProcessingException e) {
            log.error("Erro ao converter para Json");
        }
    }


}
