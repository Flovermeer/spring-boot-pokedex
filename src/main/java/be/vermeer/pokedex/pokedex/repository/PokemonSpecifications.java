package be.vermeer.pokedex.pokedex.repository;

import be.vermeer.pokedex.pokedex.model.Pokemon;
import org.springframework.data.jpa.domain.Specification;

public final class PokemonSpecifications {
    private PokemonSpecifications() {}

    public static Specification<Pokemon> nameContains(String name) {
        return (root, query, cb) ->
                // Critère neutre, comparable à SQL "1 = 1", si le filtre est absent.
                name == null || name.isBlank()
                    ? cb.conjunction()
                    : cb.like(
                            // LOWER(name) LIKE '%valeur%' : recherche partielle sans casse.
                            cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Pokemon> typeEquals(String type) {
        return (root, query, cb) -> {
            if (type == null || type.isBlank()) return cb.conjunction();

            // Une jointure peut dupliquer les lignes SQL d'un même Pokemon.
            if (query != null) query.distinct(true);

            return cb.equal(
                    // Pokemon -> relation "types" -> propriété PokemonType.name.
                    cb.lower(root.join("types").get("name")),
                    type.toLowerCase()
            );
        };
    }
}
