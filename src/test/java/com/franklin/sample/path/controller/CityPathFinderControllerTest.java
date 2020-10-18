package com.franklin.sample.path.controller;

import com.franklin.sample.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CityPathFinderControllerTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  public Object[] parametersForTestRouteExist() {
    return new Object[]{
            new Object[]{
                    "Boston",
                    "New York",
                    "yes"
            },
            new Object[]{
                    "Boston",
                    "Philadelphia",
                    "yes"
            },
            new Object[]{
                    "Boston",
                    "Albany",
                    "no"
            }
    };
  }

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).dispatchOptions(true).build();
  }

  @Test
  public void testRouteExist() throws Exception {
    assertThatPathExist("Boston", "New York", "yes");
    assertThatPathExist("Boston", "Philadelphia", "yes");
    assertThatPathExist("Boston", "Albany", "no");
  }

  public void assertThatPathExist(String origin, String destination, String pathExist) throws Exception {
    this.mockMvc.perform(get(CityPathFinderController.ENDPOINT)
            .param("origin", origin)
            .param("destination", destination))
            .andExpect(status().isOk())
            .andExpect(content().string(pathExist));
  }

  @Test
  public void testRouteExist_That_Unknown_City_Throw_Bad_Request() throws Exception {
    this.mockMvc.perform(get(CityPathFinderController.ENDPOINT)
            .param("origin", "Boston")
            .param("destination", "No Where"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Bad Request"));
  }
}
