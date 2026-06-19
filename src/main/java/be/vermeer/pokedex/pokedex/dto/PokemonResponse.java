package be.vermeer.pokedex.pokedex.dto;

import java.util.Set;

/*
 * DTO de sortie, comparable à une API Resource Laravel.
 * L'entité JPA n'est pas exposée : le contrat JSON reste indépendant
 * du modèle utilisé pour la persistance.
 */
public record PokemonResponse(
        int id,
        String name,
        Set<PokemonTypeResponse> types
) {
}
