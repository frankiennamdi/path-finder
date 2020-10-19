package com.franklin.sample.path.service;

import java.util.*;

/**
 * Vertex is comprised of data and its neighbours. This is as a list can form a Graph.
 * @param <T>
 */
public class Vertex<T extends Comparable<T>> {

  private final T info;

  private final Set<Vertex<T>> reachableNeighbours;

  public Vertex(T info) {
    this(info, new LinkedHashSet<>());
  }

  public Vertex(T info, Set<Vertex<T>> reachableNeighbours) {
    this.info = info;
    this.reachableNeighbours = reachableNeighbours;
  }

  public boolean addReachableNeighbour(T neighbour) {
    return addReachableNeighbour(new Vertex<>(neighbour));
  }

  public boolean addReachableNeighbour(Vertex<T> neighbour) {
    return this.reachableNeighbours.add(neighbour);
  }

  public T getInfo() {
    return info;
  }

  public Set<Vertex<T>> getReachableNeighbours() {
    return reachableNeighbours;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vertex<?> vertex = (Vertex<?>) o;
    return Objects.equals(info, vertex.info);
  }

  @Override
  public int hashCode() {
    return info.hashCode();
  }

  @Override
  public String toString() {
    return info.toString();
  }
}
