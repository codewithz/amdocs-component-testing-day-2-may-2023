package com.amdocs.componenttesting.service;

import com.amdocs.componenttesting.model.CustomerRegistrationRequest;
import com.amdocs.componenttesting.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService {


    CustomerRepository customerRepository;


    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerNewCustomer(CustomerRegistrationRequest customerRegistrationRequest){



    }
}
