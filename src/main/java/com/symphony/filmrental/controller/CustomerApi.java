package com.symphony.filmrental.controller;

import com.symphony.filmrental.model.entity.Customer;
import com.symphony.filmrental.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerApi {

  private final CustomerService service;

  @GetMapping("/all")
  public List<Customer> getAll() {
    return service.getAllCustomers();
  }

  @GetMapping("/{customerId}")
  public Customer getCustomer(@PathVariable("customerId") String customerId) {
    return service.getCustomer(customerId);
  }

  @PutMapping
  public void saveCustomer(@RequestBody Customer customer) {
    service.save(customer);
  }
}
