package br.com.vitheka.marketingstrategy.service;

import br.com.vitheka.marketingstrategy.model.Envelope;
import br.com.vitheka.marketingstrategy.model.ProductEvent;
import br.com.vitheka.marketingstrategy.model.SnsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;

@Service
public class CostumerEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(CostumerEventConsumer.class);

    private ObjectMapper objectMapper;

    @Autowired
    public CostumerEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${aws.sqs.queue.product.events.name}")
    public void receiveCostumerEvent(TextMessage textMessage) throws JmsException, IOException, JMSException {

        var snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);

        var envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);

        var costumerEvent = objectMapper.readValue(envelope.getData(), ProductEvent.class);

        log.info("Message ID: {}", snsMessage.getMessageId());

        log.info("Costumer event received - Event: {} - ProductId:  {} - ",
                envelope.getEventType(),
                costumerEvent.getCustomerId());

    }



}
