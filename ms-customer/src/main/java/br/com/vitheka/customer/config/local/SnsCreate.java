package br.com.vitheka.customer.config.local;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class SnsCreate {
    private static final Logger LOG = LoggerFactory.getLogger(
            SnsCreate.class);

    final private String productEventsTopic;
    final private AmazonSNS snsClient;

    @Bean
    public AmazonSNS snsClient() {
        return this.snsClient;
    }

    public SnsCreate() {
        this.snsClient = AmazonSNSClient.builder()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4566",
                                Regions.US_EAST_1.getName()))
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        CreateTopicRequest createTopicRequest = new CreateTopicRequest("product-events");
        this.productEventsTopic = this.snsClient.createTopic(createTopicRequest).getTopicArn();
        LOG.info("SNS topic ARN: {}", this.productEventsTopic);
    }

    @Bean(name = "productEventsTopic")
    public Topic snsProductEventsTopic() {
        return new Topic().withTopicArn(productEventsTopic);
    }
}
