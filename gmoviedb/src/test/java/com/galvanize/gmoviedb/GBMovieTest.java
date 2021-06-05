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
        //content().string("No movies"));
                        //jsonPath("$.id", instanceOf(Integer.class)));


    }
}
