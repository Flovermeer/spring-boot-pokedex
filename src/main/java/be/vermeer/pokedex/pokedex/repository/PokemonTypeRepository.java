package be.vermeer.pokedex.pokedex.repository;

import be.vermeer.pokedex.pokedex.model.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonTypeRepository extends JpaRepository<PokemonType, Integer> {
}
