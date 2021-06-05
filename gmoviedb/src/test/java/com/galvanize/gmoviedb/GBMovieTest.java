package com.galvanize.gmoviedb;

import com.galvanize.gmoviedb.service.GBMovieRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GBMovieTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    GBMovieRepo repo;

    @Test
    @Transactional
    @Rollback
    public void listOfMoviesEmpty() throws Exception{
        MockHttpServletRequestBuilder request = get("/User")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.id"  ).doesNotExist());
    }

    @Test
    @Transactional
    @Rollback
    public void createNewMovieTest() throws Exception{
        MockHttpServletRequestBuilder request = post("/User")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"The Avengers\",\n" +
                        "  \"director\": \"Joss Whedon\" ,\n" +
                        "  \"actors\": \"Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth\" ,\n"+
                        "  \"release\": \"2012\" ,\n" +
                                "  \"description\": \"Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.\" ,\n" +
                                "  \"rating\": \"null\"   }");
          this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.title"  ).value("The Avengers"));
    }
}
