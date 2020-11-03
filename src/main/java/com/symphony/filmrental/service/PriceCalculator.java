package com.symphony.filmrental.service;

import com.symphony.filmrental.model.Type;
import com.symphony.filmrental.model.entity.Film;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PriceCalculator {

  private final Integer basicPrice;
  private final Integer premiumPrice;

  public PriceCalculator(@Value("${price.basic}") Integer basicPrice,
                         @Value("${price.premium}") Integer premiumPrice) {
    this.basicPrice = basicPrice;
    this.premiumPrice = premiumPrice;
  }


  public Integer getPrice(Film film, Integer days) {
    switch (film.getFilmType()) {
      case REGULAR:
        return getRegularPrice(days);
      case NEW:
        return getPremiumPrice(days);
      case OLD:
        return getOldPrice(days);
      default:
        //TODO add custom exception here
        return getRegularPrice(days);
    }
  }

  public Integer getExtraFee(Type filmType, Integer daysOverdue) {
    switch (filmType) {
      case NEW:
        return daysOverdue * premiumPrice;
      case REGULAR:
      case OLD:
      default: //TODO add custom exception here
        return daysOverdue * basicPrice;
    }
  }


  private Integer getPremiumPrice(Integer days) {
    return days * premiumPrice;
  }

  private Integer getRegularPrice(Integer days) {
    if (days <= 3) {
      return basicPrice;
    } else {
      return basicPrice + (days - 3) * basicPrice;
    }
  }

  private Integer getOldPrice(Integer days) {
    if (days <= 5) {
      return basicPrice;
    } else {
      return basicPrice + (days - 5) * basicPrice;
    }
  }

}
