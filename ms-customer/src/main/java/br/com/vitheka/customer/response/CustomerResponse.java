package br.com.vitheka.customer.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerResponse {

    private Long customerId;
    private String lastName;
    private String firstName;
    private String address1;
    private String address2;
    private String city;
    private String stateProvince;
    private String email;
}
