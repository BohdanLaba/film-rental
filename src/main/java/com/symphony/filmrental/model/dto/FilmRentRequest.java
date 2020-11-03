package com.symphony.filmrental.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FilmRentRequest {
  private String customerId;
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
      timezone = "UTC")
  private Date rentStartDate;
  private List<FilmRent> films;

  public List<String> getAllFilmIds() {
    return this.films.stream().map(FilmRent::getFilmId).collect(Collectors.toList());
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FilmRent {
    private String filmId;
    private Integer days;
  }
}
