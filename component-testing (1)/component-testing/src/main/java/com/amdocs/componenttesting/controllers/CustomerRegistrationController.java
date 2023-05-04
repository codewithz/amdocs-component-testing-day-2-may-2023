package com.amdocs.componenttesting.controllers;

import com.amdocs.componenttesting.model.CustomerRegistrationRequest;
import com.amdocs.componenttesting.service.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class CustomerRegistrationController {

    private CustomerRegistrationService customerRegistrationService;

    @Autowired
    public CustomerRegistrationController(CustomerRegistrationService customerRegistrationService) {
        this.customerRegistrationService = customerRegistrationService;
    }

    @PutMapping("/customer-registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewCustomer(@Valid  @RequestBody  CustomerRegistrationRequest customerRegistrationRequest){

        customerRegistrationService.registerNewCustomer(customerRegistrationRequest);
    }

}
