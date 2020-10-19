package com.franklin.sample.path.service;

import com.google.common.base.Preconditions;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * Holds the {@link CityGraph} and the search algorithm processing the graph.
 */
@Component
public class CityPathFinder {
  private static final Logger LOGGER = LoggerFactory.getLogger(CityPathFinder.class);

  private final CityGraph cityGraph;


  @Autowired
  public CityPathFinder(@Value("${path-finder.graph.file}") String cityGraphFile,
                        @Value("${path-finder.graph.is-directed}") boolean isDirected) {
    Stream<String> cities;
    LOGGER.info("Using city graph file {}", cityGraphFile);
    try {
      ClassPathResource classPathResource = new ClassPathResource(cityGraphFile);
      cities = new BufferedReader(
              new InputStreamReader(classPathResource.getInputStream(), StandardCharsets.UTF_8))
              .lines();
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
    CityGraph cityGraph = new CityGraph(isDirected);
    cities.forEach(line -> {
      String[] vertices = line.split(",");
      if (vertices.length == 2) {
        String from = vertices[0].trim();
        String to = vertices[1].trim();
        Preconditions.checkState(Strings.isNotBlank(from), "from value cannot be blank");
        Preconditions.checkState(Strings.isNotBlank(to), "to value cannot be blank");
        cityGraph.addVertex(from);
        cityGraph.addVertex(to);
        cityGraph.addEdge(from, to);
      } else {
        throw new IllegalArgumentException("cityGraphFile is malformed requires format From,To per line");
      }
    });
    this.cityGraph = cityGraph;
  }

  public CityGraph getCityGraph() {
    return cityGraph;
  }

  public boolean pathExist(String from, String to) {
    Vertex<String> fromVertex = cityGraph.getVertex(from);
    Vertex<String> toVertex = cityGraph.getVertex(to);
    Stack<Vertex<String>> cities = new Stack<>();
    Map<Vertex<String>, Vertex<String>> visitedVertices = new HashMap<>();
    visitedVertices.put(fromVertex, fromVertex);
    cities.addAll(fromVertex.getReachableNeighbours());

    while (!cities.empty()) {
      Vertex<String> curr = cities.pop();
      if (curr.equals(toVertex)) {
        return true;
      }
      if (!visitedVertices.containsKey(curr)) {
        visitedVertices.put(curr, curr);
        cities.addAll(curr.getReachableNeighbours());
      }
    }
    return visitedVertices.containsKey(toVertex);
  }
}
