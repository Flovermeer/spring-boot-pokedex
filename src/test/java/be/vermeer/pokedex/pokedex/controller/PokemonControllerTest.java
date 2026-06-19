package be.vermeer.pokedex.pokedex.controller;

import be.vermeer.pokedex.pokedex.exception.PokemonNotFoundException;
import be.vermeer.pokedex.pokedex.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.mockito.Mockito.when;

@WebMvcTest(PokemonController.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokemonService service;

    @Test
    void showPokemonReturnsNotFound() throws Exception {
        when(service.findById(999)).thenThrow(new PokemonNotFoundException(999));
        mockMvc.perform(get("/pokemon/999")).andExpect(status().isNotFound());
    }
}
