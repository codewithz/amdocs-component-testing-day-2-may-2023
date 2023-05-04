package com.amdocs.componenttesting.controllers;

import com.amdocs.componenttesting.model.CustomerRegistrationRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class CustomerRegistrationController {


    public void registerNewCustomer(@RequestBody  CustomerRegistrationRequest customerRegistrationRequest){

    }

}
