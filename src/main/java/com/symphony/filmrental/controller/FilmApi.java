package com.symphony.filmrental.controller;

import com.symphony.filmrental.model.dto.FilmRentRequest;
import com.symphony.filmrental.model.dto.FilmRentResponse;
import com.symphony.filmrental.model.dto.FilmReturnRequest;
import com.symphony.filmrental.model.dto.FilmReturnResponse;
import com.symphony.filmrental.model.entity.Film;
import com.symphony.filmrental.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/film")
@RequiredArgsConstructor
public class FilmApi {

  private final FilmService filmService;

  @GetMapping("/all")
  public List<Film> getAll() {
    return filmService.getAllFilms();
  }

  @PostMapping("/rentFilms")
  public FilmRentResponse rentFilms(FilmRentRequest rentRequest) {
    return filmService.rentFilmsForCustomer(rentRequest);
  }

  @PostMapping("/returnFilms")
  public FilmReturnResponse returnFilms(FilmReturnRequest filmReturnRequest) {
    return filmService.checkFilmsReturnOverdue(filmReturnRequest);
  }

}
