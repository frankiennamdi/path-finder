package com.franklin.sample.path.controller;


import com.franklin.sample.path.service.CityPathFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for city path finder
 */

@RestController
@RequestMapping(CityPathFinderController.ENDPOINT)
public class CityPathFinderController {
  static final String ENDPOINT = "/connected";
  private final CityPathFinder cityPathFinder;

  @Autowired
  public CityPathFinderController(CityPathFinder cityPathFinder) {
    this.cityPathFinder = cityPathFinder;
  }

  @GetMapping
  public String routeExist(@RequestParam String origin, @RequestParam String destination) {
    return (cityPathFinder.pathExist(origin, destination)) ? "yes" : "no";
  }
}
