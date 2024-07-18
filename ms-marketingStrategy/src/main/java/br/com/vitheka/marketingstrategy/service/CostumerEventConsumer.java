package br.com.vitheka.marketingstrategy.service;

import br.com.vitheka.marketingstrategy.model.Costumer;
import br.com.vitheka.marketingstrategy.model.Envelope;
import br.com.vitheka.marketingstrategy.model.ProductEvent;
import br.com.vitheka.marketingstrategy.model.SnsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Service
public class CostumerEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(CostumerEventConsumer.class);

    private ObjectMapper objectMapper;
    private final DynamoDbTemplate dynamoDbTemplate;

    @Autowired
    public CostumerEventConsumer(ObjectMapper objectMapper, DynamoDbTemplate dynamoDbTemplate) {
        this.objectMapper = objectMapper;
        this.dynamoDbTemplate = dynamoDbTemplate;
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

        var costumerEventLog = buildCostumerEventLog(envelope, costumerEvent);

         dynamoDbTemplate.save(costumerEventLog);
    }

    private Costumer buildCostumerEventLog(Envelope envelope, ProductEvent productEvent) {

        var timestamp = Instant.now().toEpochMilli();

        var costumerEventLog = new Costumer();
        costumerEventLog.setPk(String.valueOf(productEvent.getCustomerId()));
        costumerEventLog.setSk(envelope.getEventType() + "_" + timestamp);
        costumerEventLog.setEventType(envelope.getEventType());
        costumerEventLog.setCostumerId(productEvent.getCustomerId());
        costumerEventLog.setUsername(productEvent.getUsername());
        costumerEventLog.setTimestamp(timestamp);
        costumerEventLog.setTtl(Instant.now().plus(
                Duration.ofMinutes(10)).getEpochSecond());

        return costumerEventLog;

    }



}
