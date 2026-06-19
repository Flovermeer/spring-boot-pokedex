package be.vermeer.pokedex.pokedex.repository;

import be.vermeer.pokedex.pokedex.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PokemonRepository extends
        // Fournit le CRUD et la pagination.
        JpaRepository<Pokemon, Integer>,
        // Permet d'exécuter des filtres dynamiques composés avec Specification.
        JpaSpecificationExecutor<Pokemon> {

    //public Page<Pokemon> findByNameContainingIgnoreCase(String name, Pageable pageable);
    //public Page<Pokemon> findByTypeIgnoreCase(String type, Pageable pageable);
}
