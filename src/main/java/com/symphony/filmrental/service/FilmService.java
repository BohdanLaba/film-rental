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

  public static final int PREMIUM_POINT = 2;
  public static final int BONUS_POINT = 1;

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

  //TODO divide in two methods
  public FilmReturnResponse checkFilmsReturnOverdue(FilmReturnRequest returnRequest) {
    Customer customer = customerService.getCustomer(returnRequest.getCustomerId());

    Map<String, FilmRented> returningFilms = customer.getFilmsRented().stream()
        .filter(filmRented -> returnRequest.getFilmIds().contains(filmRented.getFilmId()))
        .collect(Collectors.toMap(filmRented -> filmRented.getFilmId(), Function.identity()));

    List<Film> filmsFromDb = repository.findAllByIdIn(new ArrayList<>(returningFilms.keySet()));
    Integer extraFee = filmsFromDb
        .stream()
        .map(film ->
            calculateExtraFee(film.getFilmType(), returningFilms.get(film.getId()),
                returnRequest.getRentEndDate()))
        .reduce(0, (a, b) -> a + b);

    Integer bonusPoints = filmsFromDb.stream()
        .map(film -> calculateBonusPoints(film)).reduce(0, (a, b) -> a + b);


    customer.getFilmsRented().removeAll(returningFilms.values());
    customer.addBonusPoints(bonusPoints);
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

  private Integer calculateBonusPoints(Film film) {
    switch (film.getFilmType()) {
      case NEW:
        return PREMIUM_POINT;
      case OLD:
      case REGULAR:
      default:
        return BONUS_POINT;
    }
  }
}
