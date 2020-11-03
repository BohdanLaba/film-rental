package com.symphony.filmrental.controller;

import com.symphony.filmrental.model.entity.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerApi {

  @GetMapping("/all")
  public List<Customer> getAll() {
    return null;
  }

  @GetMapping("/{customerId}/points")
  public Integer getPoints(@PathVariable("customerId") String customerId) {
    return null;
  }
}
