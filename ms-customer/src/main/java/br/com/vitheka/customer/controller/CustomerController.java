package br.com.vitheka.customer.controller;

import br.com.vitheka.customer.mapper.CustomerMapper;
import br.com.vitheka.customer.request.CustomerRequest;
import br.com.vitheka.customer.response.CustomerResponse;
import br.com.vitheka.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @PostMapping
    public ResponseEntity<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerRequest request) {

        var customer = mapper.toCustomer(request);

        customer = customerService.saveCustomer(customer);

        var response = mapper.toCustomerResponse(customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
