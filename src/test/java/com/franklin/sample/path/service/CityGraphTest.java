package com.franklin.sample.path.service;

import com.google.common.collect.Lists;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;

@RunWith(JUnitParamsRunner.class)
public class CityGraphTest {

  private static final Logger logger = LoggerFactory.getLogger(CityGraphTest.class);

  public Object[] parametersForTestDirectedGraph() {
    return new Object[]{
            new Object[]{
                    new String[][]{{"Boston", "New York"}},
                    "Boston",
                    Lists.newArrayList("New York")
            },
            new Object[]{
                    new String[][]{{"Boston", "New York"}},
                    "New York",
                    Lists.newArrayList()
            },
    };
  }

  public Object[] parametersForTestUndirectedGraph() {
    return new Object[]{
            new Object[]{
                    new String[][]{{"Boston", "New York"}},
                    "Boston",
                    Lists.newArrayList("New York")
            },
            new Object[]{
                    new String[][]{{"Boston", "New York"}},
                    "New York",
                    Lists.newArrayList("Boston")
            },
    };
  }

  @Test
  @Parameters
  public void testDirectedGraph(String[][] connections, String originCity, List<String> expectedCitiesFromOrigin) {
    CityGraph cityGraph = new CityGraph(true);
    loadGraph(cityGraph, connections);
    logger.info(cityGraph.toString());
    Assert.assertThat(neighboursToStringList(cityGraph, originCity), equalTo(expectedCitiesFromOrigin));
  }

  @Test
  @Parameters
  public void testUndirectedGraph(String[][] connections, String originCity, List<String> expectedCitiesFromOrigin) {
    CityGraph cityGraph = new CityGraph();
    loadGraph(cityGraph, connections);
    logger.info(cityGraph.toString());
    Assert.assertThat(neighboursToStringList(cityGraph, originCity), equalTo(expectedCitiesFromOrigin));
  }

  private void loadGraph(CityGraph cityGraph, String[][] connections) {
    for (String[] arr : connections) {
      if (arr.length != 2) {
        throw new RuntimeException("city connection must be of length 2");
      }
      cityGraph.addVertex(arr[0]);
      cityGraph.addVertex(arr[1]);
      cityGraph.addEdge(arr[0], arr[1]);
    }
  }


  private List<String> neighboursToStringList(CityGraph cityGraph, String vertexName) {
    return cityGraph.getReachableNeighbours(vertexName).stream().map(Vertex::getInfo).collect(Collectors.toList());
  }
}
