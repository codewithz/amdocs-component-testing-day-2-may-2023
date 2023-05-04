package com.amdocs.componenttesting.controllers;

import com.amdocs.componenttesting.model.Customer;
import com.amdocs.componenttesting.model.CustomerRegistrationRequest;
import com.amdocs.componenttesting.service.CustomerRegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//import static  org.assertj.core.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
@WebMvcTest(CustomerRegistrationController.class)
class CustomerRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRegistrationService customerRegistrationService;

    @Test
    void itShouldRegisterANewCustomer() throws Exception {
        //Given
        //.. a customer
        UUID id=UUID.randomUUID();
        Customer customer=new Customer(id,"Mary","99999");

        //... a request
        CustomerRegistrationRequest request=new CustomerRegistrationRequest(customer);

        doNothing().when(customerRegistrationService).registerNewCustomer(request);

        //When

       ResultActions customerResultActions= mockMvc.perform(
                put("/api/v1/customer-registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectToJson(request))));



        //Then

        customerResultActions.andExpectAll(
                status().isCreated()
        );
    }

    private String objectToJson(Object object){
        try{
            return  new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e){
            fail("Failed to convert to JSON");
            return  null;
        }
    }



}