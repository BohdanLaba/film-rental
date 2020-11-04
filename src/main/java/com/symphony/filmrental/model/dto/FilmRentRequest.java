package com.symphony.filmrental.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FilmRentRequest {
  private String customerId;
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "dd-MM-yyyy",
      timezone = "UTC")
  private Date rentStartDate;
  private List<FilmRent> films;

  @Transient
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
