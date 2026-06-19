package be.vermeer.pokedex.pokedex.controller;

import be.vermeer.pokedex.pokedex.dto.CreatePokemonRequest;
import be.vermeer.pokedex.pokedex.dto.PokemonResponse;
import be.vermeer.pokedex.pokedex.dto.UpdatePokemonRequest;
import be.vermeer.pokedex.pokedex.service.PokemonService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService service) {
        this.pokemonService = service;
    }

    @GetMapping("/{id}")
    public PokemonResponse show(@PathVariable int id) {
        return pokemonService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<PokemonResponse> create(@Valid @RequestBody CreatePokemonRequest request) {
        PokemonResponse response = pokemonService.create(request);
        URI url = URI.create("/pokemon/" + response.id());
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping()
    public Page<PokemonResponse> index(Pageable pageable, @RequestParam(required = false) String name, @RequestParam(required = false) String type) {
        return pokemonService.search(name, type, pageable);
    }

    @PatchMapping("/{id}")
    public PokemonResponse update(@PathVariable int id, @Valid @RequestBody UpdatePokemonRequest request) {
        return pokemonService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        pokemonService.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
