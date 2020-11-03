package com.symphony.filmrental.service;

import com.symphony.filmrental.exception.InvalidCustomerIdException;
import com.symphony.filmrental.model.dto.FilmReturnRequest;
import com.symphony.filmrental.model.entity.Customer;
import com.symphony.filmrental.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;

  public Customer getCustomer(String customerId) {
    return repository.findById(customerId)
        .orElseThrow(InvalidCustomerIdException::new);
  }

  public void save(Customer customer) {
    repository.save(customer);
  }

  public void processCustomerFilmRent(String customerId, Map<String, Integer> films, Date rentDate) {
    Customer customer = getCustomer(customerId);

    customer.addRentedFilms(films.entrySet().stream()
        .map(film -> new Customer.FilmRented(film.getKey(), film.getValue(), rentDate))
        .collect(Collectors.toList()));

    save(customer);
  }

}
