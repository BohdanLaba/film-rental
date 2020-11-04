package com.symphony.filmrental.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Document(collection = "customers")
public class Customer {
  @Id
  private String id;
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  private Integer bonusPoints;


  private List<FilmRented> filmsRented;

  public void addRentedFilms(List<FilmRented> filmsRented) {
    if (Objects.isNull(this.filmsRented)) {
      this.filmsRented = new ArrayList<>();
    }
    this.filmsRented.addAll(filmsRented);
  }

  public void addBonusPoints(Integer points) {
    if (Objects.isNull(this.bonusPoints)) {
      this.bonusPoints = points;
    } else {
      this.bonusPoints += points;
    }
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
