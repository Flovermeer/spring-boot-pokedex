package be.vermeer.pokedex.pokedex.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CreatePokemonRequest {
    @NotBlank
    @Size(min=1, max=30)
    private String name;

    /*
     * L'API reçoit les identifiants de types existants plutôt que des noms libres.
     * Set élimine les doublons ; les annotations imposent entre un et deux types.
     */
    @NotEmpty
    @Size(max=2)
    private Set<Integer> typeIds;

    public String getName() { return this.name; }
    public Set<Integer> getTypeIds() { return this.typeIds; }
    public void setName(String name) { this.name = name; }
    public void setTypeIds(Set<Integer> typeIds) { this.typeIds = typeIds; }
}
