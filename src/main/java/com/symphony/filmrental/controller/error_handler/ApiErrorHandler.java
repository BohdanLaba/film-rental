package com.symphony.filmrental.controller.error_handler;

import com.symphony.filmrental.controller.FilmApi;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackageClasses = FilmApi.class)
public class ApiErrorHandler {

}
