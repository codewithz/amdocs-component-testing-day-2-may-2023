package com.amdocs.componenttesting.repository;

import com.amdocs.componenttesting.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
}
