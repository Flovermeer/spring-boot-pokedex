package be.vermeer.pokedex.pokedex.mapper;

import be.vermeer.pokedex.pokedex.dto.PokemonTypeResponse;
import be.vermeer.pokedex.pokedex.model.PokemonType;

public final class PokemonTypeMapper {
    private PokemonTypeMapper() {}

    public static PokemonTypeResponse toResponse(PokemonType type) {
        return new PokemonTypeResponse(
                type.getId(),
                type.getName()
        );
    }
}
