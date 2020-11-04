package com.symphony.filmrental.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FilmReturnRequest {
  private String customerId;
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "dd-MM-yyyy",
      timezone = "UTC")
  private Date rentEndDate;

  @JsonProperty("films")
  private List<String> filmIds;
}
