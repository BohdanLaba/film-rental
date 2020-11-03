package com.symphony.filmrental.model.entity;

import com.symphony.filmrental.model.Type;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "films")
public class Film {
  @Id
  private String id;
  private String name;
  private Type filmType;
  private Integer quantity;

}
