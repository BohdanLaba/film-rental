package com.symphony.filmrental.model.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FilmRentResponse {
  public Map<String, Integer> films = new HashMap<>();
  public Integer total;

  public void addFilm(String filmName, Integer cost) {
    films.put(filmName, cost);
  }
}
