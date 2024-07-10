package br.com.vitheka.marketingstrategy.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class CostumerEventKey {

    private String pk;
    private String sk;


    public CostumerEventKey() {} //constructor empty

    @DynamoDBHashKey(attributeName = "pk")
    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @DynamoDBRangeKey(attributeName = "sk")
    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }
}
