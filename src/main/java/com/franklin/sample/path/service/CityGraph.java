package com.franklin.sample.path.service;

import java.util.*;

/**
 * Graph of the connections of the cities
 */
public class CityGraph {
  // store the vertices in a map for improved load and processing time.
  private final Map<String, Vertex<String>> vertices;

  // indicate if the graph is a directed graph, false by default
  private final boolean isDirected;

  public CityGraph() {
    this(false);
  }

  public CityGraph(boolean isDirected) {
    this.isDirected = isDirected;
    this.vertices = new LinkedHashMap<>();
  }

  public Vertex<String> getVertex(String info) {
    return validateAndGetVertex(info);
  }

  public Set<Vertex<String>> getReachableNeighbours(String info) {
    return validateAndGetVertex(info).getReachableNeighbours();
  }

  public boolean addVertex(String info) {
    if (!vertices.containsKey(info)) {
      vertices.put(info, new Vertex<>(info));
      return true;
    }
    return false;
  }

  public void addEdge(String from, String to) {
    if (isDirected)
      addDirectedEdge(from, to);
    else
      addUndirectedEdge(from, to);
  }

  private void addDirectedEdge(String from, String to) {
    validateAndGetVertex(from).addReachableNeighbour(validateAndGetVertex(to));
  }

  private void addUndirectedEdge(String from, String to) {

    Vertex<String> fromVertex = validateAndGetVertex(from);
    Vertex<String> toVertex = validateAndGetVertex(to);

    fromVertex.addReachableNeighbour(toVertex);
    toVertex.addReachableNeighbour(fromVertex);
  }

  private Vertex<String> validateAndGetVertex(String info) {
    return Optional.ofNullable(vertices.get(info))
            .orElseThrow(() -> new IllegalArgumentException(String.format("No such vertex %s", info)));
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (Vertex<String> vertex : vertices.values()) {
      for (Vertex<String> neighbour : vertex.getReachableNeighbours()) {
        sb.append(String.format("%s -> %s\n", vertex.toString(), neighbour.toString()));
      }
    }
    return sb.toString();
  }
}
