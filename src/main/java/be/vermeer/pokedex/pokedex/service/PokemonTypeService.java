package be.vermeer.pokedex.pokedex.service;

import be.vermeer.pokedex.pokedex.dto.PokemonTypeResponse;
import be.vermeer.pokedex.pokedex.exception.PokemonTypeNotFoundException;
import be.vermeer.pokedex.pokedex.mapper.PokemonTypeMapper;
import be.vermeer.pokedex.pokedex.repository.PokemonTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PokemonTypeService {

    private final PokemonTypeRepository repository;

    public PokemonTypeService(PokemonTypeRepository pokemonTypeRepository) {
        this.repository = pokemonTypeRepository;
    }

    @Transactional(readOnly = true)
    public PokemonTypeResponse findById(int id) {
        return PokemonTypeMapper.toResponse(repository.findById(id).orElseThrow(PokemonTypeNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public Page<PokemonTypeResponse> findAll(Pageable pageable) {
        return this.repository.findAll(pageable).map(PokemonTypeMapper::toResponse);
    }
}
