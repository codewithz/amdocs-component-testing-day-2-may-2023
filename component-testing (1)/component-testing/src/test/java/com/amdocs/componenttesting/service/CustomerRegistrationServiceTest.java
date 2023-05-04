package com.amdocs.componenttesting.service;

import com.amdocs.componenttesting.model.Customer;
import com.amdocs.componenttesting.model.CustomerRegistrationRequest;
import com.amdocs.componenttesting.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;

//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class CustomerRegistrationServiceTest {

    private CustomerRegistrationService underTest;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;


    @Mock
    private CustomerRepository customerRepository;
//    private CustomerRepository customerRepository= Mockito.mock(CustomerRepository.class);

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest=new CustomerRegistrationService(customerRepository);
    }

    @Test
    void itShouldSaveCustomer() {
        //Given
        // .. a phone number and a customer
        String phoneNumber="111111";
        Customer customer=new Customer(UUID.randomUUID(),"Mary",phoneNumber);
        //.. a request
        CustomerRegistrationRequest request=new CustomerRegistrationRequest(customer);
        //.. No customer should exist for the given phone number -- MOCKED
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());
        //When
        underTest.registerNewCustomer(request);
        //Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue=customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void itShouldSaveCustomerWhenIdIsNull() {
        //Given
        // .. a phone number and a customer
        String phoneNumber="111111";
        Customer customer=new Customer(null,"Mary",phoneNumber);
        //.. a request
        CustomerRegistrationRequest request=new CustomerRegistrationRequest(customer);
        //.. No customer should exist for the given phone number -- MOCKED
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());
        //When
        underTest.registerNewCustomer(request);
        //Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue=customerArgumentCaptor.getValue();
//        customerArgumentCaptorValue.setId(UUID.randomUUID());
        assertThat(customerArgumentCaptorValue).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(customer);

    }

    @Test
    void itShouldNotSaveACustomerWhenCustomerExists() {
        //Given
        // .. a phone number and a customer
        String phoneNumber="111111";
        Customer customer=new Customer(UUID.randomUUID(),"Mary",phoneNumber);
        //.. a request
        CustomerRegistrationRequest request=new CustomerRegistrationRequest(customer);
        //.. Return a customer for given phone number -- MOCKED
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customer));
        //When
        underTest.registerNewCustomer(request);
        //Then
        then(customerRepository).should(never()).save(any());
///     then(customerRepository).shouldHaveNoMoreInteractions();
// /       then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void itShouldThrowAnExceptionWhenPhoneNumberIsTaken() {
        //Given
        //.. given a phone number and two customers
        String phoneNumber="987654321";
        Customer joseph=new Customer(UUID.randomUUID(),"Joseph",phoneNumber);
        Customer elizabeth=new Customer(UUID.randomUUID(),"Elizabeth",phoneNumber);

        //.. a request
        CustomerRegistrationRequest request=new CustomerRegistrationRequest(joseph);

        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.of(elizabeth));
        //When

        //Then
        assertThatThrownBy(()-> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Phone Number [%s] is taken",phoneNumber));

        then(customerRepository).should(never()).save(any());

    }
}