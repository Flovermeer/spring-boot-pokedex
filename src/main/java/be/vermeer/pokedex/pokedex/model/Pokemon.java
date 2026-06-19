package be.vermeer.pokedex.pokedex.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Pokemon {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    /*
     * Un Pokemon peut avoir plusieurs types et un type peut appartenir à
     * plusieurs Pokemon : la relation est donc ManyToMany.
     *
     * Pokemon est propriétaire de l'association. Modifier cette collection
     * puis sauvegarder le Pokemon met à jour la table intermédiaire.
     */
    @ManyToMany
    @JoinTable(
        name = "pokemon_type_assignments",
        joinColumns = @JoinColumn(name="pokemon_id"),
        inverseJoinColumns = @JoinColumn(name="type_id")
    )
    private Set<PokemonType> types = new HashSet<>();

    protected Pokemon() {}

    public Pokemon(String name, Set<PokemonType> types) {
        setName(name);
        setTypes(types);
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public Set<PokemonType> getTypes() { return this.types; }

    public void setName(String name) { this.name = name; }
    public void setTypes(Set<PokemonType> inTypes) {
       // Copie défensive : la collection interne ne partage pas la référence reçue.
       types = new HashSet<>(inTypes);
    }
}
