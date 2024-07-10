package br.com.vitheka.customer.service;

import br.com.vitheka.customer.entity.Customer;
import br.com.vitheka.customer.exception.EmailAlreadyInUse;
import br.com.vitheka.customer.repository.CustomerRepository;
import br.com.vitheka.customer.enums.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CostumerPublisher costumerPublisher;

    public Customer saveCustomer(Customer customerIn) {

        if (customerRepository.findByEmail(customerIn.getEmail()).isPresent()) {
            throw new EmailAlreadyInUse("Email already in use!");
        }

        var customerSaved = customerRepository.save(customerIn);

        costumerPublisher.publishCostumerEvent(customerSaved, EventType.CUSTOMER_CREATED, "atendente");

        return customerSaved;
    }
}
