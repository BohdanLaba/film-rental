package com.symphony.filmrental.repository;

import com.symphony.filmrental.model.entity.Film;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends MongoRepository<Film, String> {
  @Cacheable(value = "films", key = "#id")
  Film findFilmById(String id);

  List<Film> findAllByIdIn(List<String> filmIds);
}
