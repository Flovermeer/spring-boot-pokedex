package be.vermeer.pokedex.pokedex.controller;

import be.vermeer.pokedex.pokedex.dto.PokemonTypeResponse;
import be.vermeer.pokedex.pokedex.service.PokemonTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon-types")
public class PokemonTypeController {

    private final PokemonTypeService service;

    public PokemonTypeController(PokemonTypeService pokemonTypeService) {
        this.service = pokemonTypeService;
    }

    @GetMapping
    public Page<PokemonTypeResponse> index(@PageableDefault(sort="name") Pageable pageable) {
        return this.service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PokemonTypeResponse show(@PathVariable int id) {
        return this.service.findById(id);
    }

}
