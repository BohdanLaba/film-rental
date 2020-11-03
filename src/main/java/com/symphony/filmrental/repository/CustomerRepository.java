package com.symphony.filmrental.repository;

import com.symphony.filmrental.model.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends MongoRepository<Customer, String> {

}
