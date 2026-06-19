package be.vermeer.pokedex.pokedex.mapper;

import be.vermeer.pokedex.pokedex.dto.PokemonResponse;
import be.vermeer.pokedex.pokedex.model.Pokemon;

import java.util.stream.Collectors;

public final class PokemonMapper {
    private PokemonMapper() {}

    public static PokemonResponse toResponse(Pokemon pokemon) {
        return new PokemonResponse(
                pokemon.getId(),
                pokemon.getName(),
                pokemon.getTypes()
                        .stream()
                        // Chaque entité liée devient un DTO imbriqué.
                        .map(PokemonTypeMapper::toResponse)
                        // Une opération terminale matérialise le Stream dans un Set.
                        .collect(Collectors.toSet())
        );
    }
}
