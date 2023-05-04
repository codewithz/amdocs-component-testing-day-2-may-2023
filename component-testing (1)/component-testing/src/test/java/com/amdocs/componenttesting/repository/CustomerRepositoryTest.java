package com.amdocs.componenttesting.repository;

import com.amdocs.componenttesting.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

//import static org.junit.jupiter.api.Assertions.*;
import static  org.assertj.core.api.Assertions.*;
@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository underTest;


    @Test
    void itShouldSelectCustomerByPhoneNumber() {
        //Given
        UUID id=UUID.randomUUID();
        String phoneNumber="12345";
        Customer customer=new Customer(id,"Mary",phoneNumber);

        underTest.save(customer);
        //When
        Optional<Customer> optionalCustomer=underTest.selectCustomerByPhoneNumber(customer.getPhoneNumber());
        //Then
        Customer selectedCustomer=optionalCustomer.get();
        assertThat(optionalCustomer).isPresent();
        assertThat(selectedCustomer.getPhoneNumber()).isEqualTo(customer.getPhoneNumber());
        assertThat(selectedCustomer).usingRecursiveComparison().isEqualTo(customer);

    }

    @Test
    void itShouldNotSelectACustomerByPhoneNumberWhenCustomerDoesNotExist() {
        //Given
        String phoneNumber="12345";
        //When
        Optional<Customer> optionalCustomer=underTest.selectCustomerByPhoneNumber(phoneNumber);
        //Then
        assertThat(optionalCustomer).isNotPresent();
    }

    @Test
    void itShouldSaveCustomer() {
        //Given
        UUID id=UUID.randomUUID();
        String name="Alex";
        String phoneNumber="12345";

        Customer customer=new Customer(id,name,phoneNumber);
        //When
        underTest.save(customer);
        //Then
        Optional<Customer> optionalCustomer=underTest.findById(id);
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c ->{
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo(name);
                    assertThat(c.getPhoneNumber()).isEqualTo(phoneNumber);
                });
//
//        assertThat(optionalCustomer).isPresent();
//        assertThat(optionalCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void itShouldNotSaveCustomerWhenNameIsNull() {
        //Given
        UUID id=UUID.randomUUID();
        String name=null;
        String phoneNumber="12345";

        Customer customer=new Customer(id,name,phoneNumber);
        //When
//        underTest.save(customer);
        //Then
        assertThatThrownBy(()->underTest.save(customer))
                .hasMessageContaining("not-null property references a null " +
                        "or transient value : com.amdocs.componenttesting.model.Customer.name")
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}