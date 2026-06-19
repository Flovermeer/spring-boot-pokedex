package be.vermeer.pokedex.pokedex.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pokemon_types")
public class PokemonType {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    protected PokemonType() {} //constructeur vide pour JPA

    public PokemonType(String name) {
        this.name = name;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
}
