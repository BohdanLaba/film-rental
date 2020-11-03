package com.symphony.filmrental.service;

import com.symphony.filmrental.model.Type;
import com.symphony.filmrental.model.dto.FilmRentRequest;
import com.symphony.filmrental.model.dto.FilmRentResponse;
import com.symphony.filmrental.model.dto.FilmReturnRequest;
import com.symphony.filmrental.model.dto.FilmReturnResponse;
import com.symphony.filmrental.model.entity.Customer;
import com.symphony.filmrental.model.entity.Customer.FilmRented;
import com.symphony.filmrental.model.entity.Film;
import com.symphony.filmrental.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

  private final FilmRepository repository;

  private final PriceCalculator priceCalculator;
  private final CustomerService customerService;


  public List<Film> getAllFilms() {
    return repository.findAll();
  }

  public FilmRentResponse rentFilmsForCustomer(FilmRentRequest rentRequest) {
    List<Film> films = repository.findAllByIdIn(new ArrayList<>(rentRequest.getAllFilmIds()));
    Map<String, Integer> idToDaysMap = rentRequest.getFilms().stream()
        .collect(Collectors.toMap(filmRent -> filmRent.getFilmId(), filmRent -> filmRent.getDays()));

    FilmRentResponse response = new FilmRentResponse();
    Integer totalPrice = 0;

    for (Film film : films) {
      Integer cost = priceCalculator.getPrice(film, idToDaysMap.get(film.getId()));
      response.addFilm(film.getName(), cost);
      totalPrice += cost;
    }

    response.setTotal(totalPrice);

    customerService
        .processCustomerFilmRent(rentRequest.getCustomerId(), idToDaysMap, rentRequest.getRentStartDate());
    return response;
  }

  public FilmReturnResponse checkFilmsReturnOverdue(FilmReturnRequest returnRequest) {
    Customer customer = customerService.getCustomer(returnRequest.getCustomerId());

    Map<String, FilmRented> retunrningFilms = customer.getFilmsRented().stream()
        .filter(filmRented -> returnRequest.getFilmIds().contains(filmRented.getFilmId()))
        .collect(Collectors.toMap(filmRented -> filmRented.getFilmId(), Function.identity()));

    Integer extraFee = repository.findAllByIdIn(new ArrayList<>(retunrningFilms.keySet()))
        .stream().map(film ->
            calculateExtraFee(film.getFilmType(), retunrningFilms.get(film.getId()),
                returnRequest.getRentEndDate())).reduce(0, (a, b) -> a + b);


    customer.getFilmsRented().removeAll(retunrningFilms.values());
    customerService.save(customer);

    FilmReturnResponse returnResponse = new FilmReturnResponse();
    returnResponse.setExtraFee(extraFee);
    return returnResponse;
  }

  private Integer calculateExtraFee(Type filmType, FilmRented filmRented, Date rentEndDate) {
    Long actualDaysRented = daysDiff(filmRented.getRentDate(), rentEndDate);
    Integer daysDiff = actualDaysRented.intValue() - filmRented.getDays();
    if (daysDiff > 0) {
      return priceCalculator.getExtraFee(filmType, daysDiff);
    } else {
      return 0;
    }
  }

  private long daysDiff(Date startDate, Date endDate) {
    long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
    return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
  }


}
