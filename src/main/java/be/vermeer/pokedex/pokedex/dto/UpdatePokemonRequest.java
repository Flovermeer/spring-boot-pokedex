package be.vermeer.pokedex.pokedex.dto;

import jakarta.validation.constraints.Size;

import java.util.Set;

public class UpdatePokemonRequest {
    @Size(min=1, max=30)
    private String name;

    /*
     * Pour un PATCH, null signifie "champ absent : ne pas modifier".
     * @Size accepte null, mais valide la collection lorsqu'elle est fournie.
     */
    @Size(min=1, max=2)
    private Set<Integer> typeIds;

    public String getName() { return this.name; }
    public Set<Integer> getTypeIds() { return this.typeIds; }
    public void setName(String name) { this.name = name; }
    public void setTypeIds(Set<Integer> typeIds) { this.typeIds = typeIds; }
}
