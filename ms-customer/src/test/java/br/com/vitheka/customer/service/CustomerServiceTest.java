package br.com.vitheka.customer.service;

import br.com.vitheka.customer.entity.Customer;
import br.com.vitheka.customer.exception.EmailAlreadyInUse;
import br.com.vitheka.customer.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CostumerPublisher costumerPublisher;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;


    @BeforeEach
    void init() {

        customer = Customer.builder()
                .customerId(1L)
                .lastName("Silva")
                .firstName("João")
                .address1("Rua das Flores, 123")
                .address2("Apto 45")
                .city("Criciúma")
                .stateProvince("Santa Catarina")
                .email("joao.silva@example.com")
                .build();
    }


    @Test
    @DisplayName("saveCustomer() return Customer when Successful")
    @Order(1)
    void saveCustomer_ReturnCustomer_WhenSuccessful() {

        when(customerRepository.save(customer)).thenReturn(customer);

        var response = customerService.saveCustomer(customer);

        Assertions.assertThat(response)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .isEqualTo(customer);


    }

    @Test
    @DisplayName("saveCustomer() Throw EmailAlreadyInUse")
    @Order(2)
    void saveCustomer_ThrowEmailAlreadyInUse_WhenEmailAlreadyInUse() {

        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        Assertions.assertThatException()
                .isThrownBy(() -> customerService.saveCustomer(customer))
                .isInstanceOf(EmailAlreadyInUse.class);

    }

}