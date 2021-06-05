package com.galvanize.gmoviedb;

import com.galvanize.gmoviedb.service.GBMovieRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;

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
    public void listOfMoviesEmpty() throws Exception {
        MockHttpServletRequestBuilder request = get("/Movies")
                .contentType(MediaType.APPLICATION_JSON);
        //We delete existing records from db
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    @Transactional
    @Rollback
    public void createNewMovieTest() throws Exception {
        MockHttpServletRequestBuilder request = post("/Movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"The Avengers\",\n" +
                        "  \"director\": \"Joss Whedon\" ,\n" +
                        "  \"actors\": \"Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth\" ,\n" +
                        "  \"release\": \"2012\" ,\n" +
                        "  \"description\": \"Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.\" ,\n" +
                        "  \"rating\": \"null\"   }");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Avengers"));
    }

    @Test
    @Transactional
    @Rollback
    public void listMoviesTest() throws Exception {

        String json = getJSON("/movies.json");

        MockHttpServletRequestBuilder request = post("/Movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        this.mvc.perform(request)
                .andExpect(status().isOk());

        MockHttpServletRequestBuilder getRequest = get("/Movies")
                .contentType(MediaType.APPLICATION_JSON);


        this.mvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].title", is("The Avengers")));
    }

    public String getJSON(String path) throws Exception {
        return new String(this.getClass().getResourceAsStream(path).readAllBytes());

    }

    @Test
    @Transactional
    @Rollback
    public void renderAllMovies() throws Exception {

        MockHttpServletRequestBuilder getRequest = get("/Movies")
                .contentType(MediaType.APPLICATION_JSON);


        this.mvc.perform(getRequest)
                .andExpect(status().isOk());

    }

    @Test
    @Transactional
    @Rollback
    public void finAllMoviesExisted() throws Exception {
        MockHttpServletRequestBuilder request = post("/User")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"The Avengers\",\n" +
                        "  \"director\": \"Joss Whedon\" ,\n" +
                        "  \"actors\": \"Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth\" ,\n" +
                        "  \"release\": \"2012\" ,\n" +
                        "  \"description\": \"Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.\" ,\n" +
                        "  \"rating\": \"null\"   }");
        this.mvc.perform(request)
                .andExpect(status().isOk());

        MockHttpServletRequestBuilder request1 = get("/User");

        this.mvc.perform(request1)
                .andExpect(status().isOk());


    }

    @Test
    public void testUserFiltersMoviesByTitle() throws Exception{

        MockHttpServletRequestBuilder request = get("/Movie?title=The Avengers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
               .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("The Avengers")));

    }

    @Test
    @DisplayName("Testing when the movie does not exist from the catalog, then display friendly message")
    public void testMoviesByTitleNotExists() throws Exception{

        MockHttpServletRequestBuilder request = get("/Movie?title=Dummy")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Not existent movie"));

    }


}