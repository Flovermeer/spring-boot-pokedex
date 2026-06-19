package be.vermeer.pokedex.pokedex.service;

import be.vermeer.pokedex.pokedex.dto.CreatePokemonRequest;
import be.vermeer.pokedex.pokedex.dto.PokemonResponse;
import be.vermeer.pokedex.pokedex.dto.UpdatePokemonRequest;
import be.vermeer.pokedex.pokedex.exception.PokemonNotFoundException;
import be.vermeer.pokedex.pokedex.exception.PokemonTypeNotFoundException;
import be.vermeer.pokedex.pokedex.mapper.PokemonMapper;
import be.vermeer.pokedex.pokedex.model.Pokemon;
import be.vermeer.pokedex.pokedex.model.PokemonType;
import be.vermeer.pokedex.pokedex.repository.PokemonRepository;
import be.vermeer.pokedex.pokedex.repository.PokemonSpecifications;
import be.vermeer.pokedex.pokedex.repository.PokemonTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class PokemonService {

    private final PokemonRepository repository;
    private final PokemonTypeRepository typeRepository;

    public PokemonService(PokemonRepository pokemonRepository, PokemonTypeRepository pokemonTypeRepository) {
        this.repository = pokemonRepository;
        this.typeRepository = pokemonTypeRepository;
    }

    /**
     * Ancienne version : exposait directement l'entité JPA,
     * remplacé par search() qui retourne des PokemonResponse
    public Page<Pokemon> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
     */

    public Page<PokemonResponse> search(String name, String type, Pageable pageable) {
        // Chaque filtre absent retourne un critère neutre ; les critères se composent.
        Specification<Pokemon> specification = PokemonSpecifications.nameContains(name)
                .and(PokemonSpecifications.typeEquals(type));

        // Page.map conserve les métadonnées de pagination en convertissant le contenu.
        return repository.findAll(specification, pageable).map(PokemonMapper::toResponse);
    }

    public PokemonResponse findById(int id) {
        return PokemonMapper.toResponse(findEntityById(id));
    }

    private Pokemon findEntityById(int id) {
        // Optional centralise ici la règle métier "Pokemon absent = exception".
        return this.repository.findById(id).orElseThrow(() -> new PokemonNotFoundException(id));
    }

    private Set<PokemonType> getPokemonTypesByIds(Set<Integer> ids) {
        /*
         * findAllById ignore les IDs inexistants. La comparaison des tailles
         * garantit que tous les types demandés ont réellement été trouvés.
         */
        List<PokemonType> types = this.typeRepository.findAllById(ids);
        if(types.size() != ids.size()) throw new PokemonTypeNotFoundException();
        return new HashSet<>(types);
    }

    public PokemonResponse create(CreatePokemonRequest request) {
        Set<PokemonType> types = getPokemonTypesByIds(request.getTypeIds());
        Pokemon pokemon = new Pokemon(request.getName(), types);
        return PokemonMapper.toResponse(this.repository.save(pokemon));
    }

    public PokemonResponse update(int id, UpdatePokemonRequest request) {
        Pokemon pokemon = this.findEntityById(id);
        if(request.getName() != null) pokemon.setName(request.getName());

        // En PATCH, null signifie que le client ne modifie pas cette relation.
        if(request.getTypeIds() != null) {
            Set<PokemonType> types = getPokemonTypesByIds(request.getTypeIds());
            pokemon.setTypes(types);
        }
        return PokemonMapper.toResponse(this.repository.save(pokemon));
    }

    public void delete(int id) {
        Pokemon pokemon = this.findEntityById(id);
        this.repository.delete(pokemon);
    }

  /*  public Page<Pokemon> findByName(String name, Pageable pageable) {
        return this.repository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Pokemon> findByType(String type, Pageable pageable) {
        return this.repository.findByTypeIgnoreCase(type, pageable);
    } */
}
