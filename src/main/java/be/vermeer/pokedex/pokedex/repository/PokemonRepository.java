package be.vermeer.pokedex.pokedex.repository;

import be.vermeer.pokedex.pokedex.model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PokemonRepository extends
        // Fournit le CRUD et la pagination.
        JpaRepository<Pokemon, Integer>,
        // Permet d'exécuter des filtres dynamiques composés avec Specification.
        JpaSpecificationExecutor<Pokemon> {

    //public Page<Pokemon> findByNameContainingIgnoreCase(String name, Pageable pageable);
    //public Page<Pokemon> findByTypeIgnoreCase(String type, Pageable pageable);

    /*
     * Charge les types en même temps que les Pokemon afin d'éviter le N+1.
     *
     * Limite connue : avec une relation collection + Pageable, Hibernate affiche
     * HHH90003004 et peut appliquer la pagination en mémoire. Cette solution est
     * conservée temporairement pour l'apprentissage et devra être remplacée
     * avant d'utiliser cette API avec un volume important de données.
     */
    @Override
    @EntityGraph(attributePaths = "types")
    Page<Pokemon> findAll(
            Specification<Pokemon> specification,
            Pageable pageable
    );
}
