package com.franklin.sample.path.service;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.equalTo;


@RunWith(JUnitParamsRunner.class)
public class CityPathFinderTest {

  private static final Logger logger = LoggerFactory.getLogger(CityPathFinderTest.class);

  public Object[] parametersForTestUndirectedGraph_Can_Find_Path() {
    return new Object[]{
            new Object[]{
                    "Boston",
                    "New York",
                    true
            },
            new Object[]{
                    "Boston",
                    "Philadelphia",
                    true
            },
            new Object[]{
                    "Boston",
                    "Albany",
                    false
            }
    };
  }

  public Object[] parametersForTestUndirectedGraph_With_Circle_Can_Find_Path() {
    return new Object[]{
            new Object[]{
                    "Boston",
                    "New York",
                    true
            },
            new Object[]{
                    "Boston",
                    "Philadelphia",
                    true
            },
            new Object[]{
                    "Boston",
                    "Albany",
                    false
            },
            new Object[]{
                    "New York",
                    "New York",
                    true
            }
    };
  }


  public Object[] parametersForTestDirectedGraph_With_Circle_Can_Find_Path() {
    return new Object[]{
            new Object[]{
                    "Boston",
                    "New York",
                    true
            },
            new Object[]{
                    "Boston",
                    "Philadelphia",
                    false
            },
            new Object[]{
                    "Boston",
                    "Albany",
                    false
            },
            new Object[]{
                    "New York",
                    "New York",
                    true
            }
    };
  }

  @Test
  @Parameters
  public void testUndirectedGraph_Can_Find_Path(String origin, String destination, boolean pathExist) {
    CityPathFinder cityPathFinder = new CityPathFinder("city.txt", false);
    logger.info(cityPathFinder.getCityGraph().toString());
    Assert.assertThat(cityPathFinder.pathExist(origin, destination), equalTo(pathExist));
  }

  @Test
  @Parameters
  public void testUndirectedGraph_With_Circle_Can_Find_Path(String origin, String destination, boolean pathExist) {
    CityPathFinder cityPathFinder = new CityPathFinder("service/city_with_circle.txt", false);
    logger.info(cityPathFinder.getCityGraph().toString());
    Assert.assertThat(cityPathFinder.pathExist(origin, destination), equalTo(pathExist));
  }

  @Test
  @Parameters
  public void testDirectedGraph_With_Circle_Can_Find_Path(String origin, String destination, boolean pathExist) {
    CityPathFinder cityPathFinder = new CityPathFinder("service/city_with_circle.txt", true);
    logger.info(cityPathFinder.getCityGraph().toString());
    Assert.assertThat(cityPathFinder.pathExist(origin, destination), equalTo(pathExist));
  }
}
