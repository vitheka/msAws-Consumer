package br.com.vitheka.customer.mapper;

import br.com.vitheka.customer.entity.Customer;
import br.com.vitheka.customer.request.CustomerRequest;
import br.com.vitheka.customer.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {


    Customer toCustomer(CustomerRequest request);

    CustomerResponse toCustomerResponse(Customer customer);
}
