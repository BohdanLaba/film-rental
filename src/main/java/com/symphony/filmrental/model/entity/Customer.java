package com.symphony.filmrental.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Document(collection = "customers")
public class Customer {
  @Id
  private String id;
  private String firstName;
  private String lastName;
  private Integer bonusPoints;


  private List<FilmRented> filmsRented;

  public void addRentedFilms(List<FilmRented> filmsRented) {
    if (Objects.isNull(filmsRented)) {
      filmsRented = new ArrayList<>();
    }
    this.filmsRented.addAll(filmsRented);
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FilmRented {
    private String filmId;

    //TODO rework next 2 in dueDate
    private Integer days;
    private Date rentDate;
  }
}
