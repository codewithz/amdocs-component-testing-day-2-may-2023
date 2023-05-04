package com.amdocs.componenttesting.service;

import com.amdocs.componenttesting.model.Customer;
import com.amdocs.componenttesting.model.CustomerRegistrationRequest;
import com.amdocs.componenttesting.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRegistrationService {


    CustomerRepository customerRepository;


    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerNewCustomer(CustomerRegistrationRequest customerRegistrationRequest){

            // 1. Check if Phone Number is Taken
            // 2. If Taken, lets check if it belongs to same customer
            //  2.1 If it belongs to an existing customer , return
            //  2.2 Throw an exception
           // 3. Save the customer

        Customer customerFromRequest=customerRegistrationRequest.getCustomer();
        String phoneNumber=customerFromRequest.getPhoneNumber();

        Optional<Customer> optionalCustomer=customerRepository.selectCustomerByPhoneNumber(phoneNumber);

        if(optionalCustomer.isPresent()){
            Customer customer=optionalCustomer.get();

            if(customer.getName().equals(customerFromRequest.getName())){
                return;
            }

            throw new IllegalStateException(String.format("Phone Number [%s] is taken",phoneNumber));
        }
        customerRepository.save(customerFromRequest);

    }
}
