package br.com.vitheka.marketingstrategy.model;

import br.com.vitheka.marketingstrategy.enums.EventType;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "costumer-events")
public class CostumerEventLog {


    public CostumerEventLog() {} //constructor empty

    @Id
    private CostumerEventKey costumerEventKey;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "eventType")
    private EventType eventType;
    @DynamoDBAttribute(attributeName = "costumerId")
    private long costumerId;
    @DynamoDBAttribute(attributeName = "username")
    private String username;
    @DynamoDBAttribute(attributeName = "timestamp")
    private long timestamp;
    @DynamoDBAttribute(attributeName = "ttl")
    private long ttl;

    @DynamoDBHashKey(attributeName = "pk")
    public String getPk() {
        return this.costumerEventKey != null ? this.costumerEventKey.getPk() : null;
    }

    public void setPk(String pk) {
        if (this.costumerEventKey == null) {
            this.costumerEventKey = new CostumerEventKey();
        }
        this.costumerEventKey.setPk(pk);
    }

    @DynamoDBRangeKey(attributeName = "sk")
    public String getSk() {
        return this.costumerEventKey != null ? this.costumerEventKey.getSk() : null;
    }

    public void setSk(String sk) {
        if (this.costumerEventKey == null) {
            this.costumerEventKey = new CostumerEventKey();
        }
        this.costumerEventKey.setSk(sk);
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public long getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(long costumerId) {
        this.costumerId = costumerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}
