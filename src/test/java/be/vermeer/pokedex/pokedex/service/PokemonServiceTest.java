package be.vermeer.pokedex.pokedex.service;

import be.vermeer.pokedex.pokedex.dto.CreatePokemonRequest;
import be.vermeer.pokedex.pokedex.dto.PokemonResponse;
import be.vermeer.pokedex.pokedex.dto.UpdatePokemonRequest;
import be.vermeer.pokedex.pokedex.exception.PokemonNotFoundException;
import be.vermeer.pokedex.pokedex.exception.PokemonTypeNotFoundException;
import be.vermeer.pokedex.pokedex.model.Pokemon;
import be.vermeer.pokedex.pokedex.model.PokemonType;
import be.vermeer.pokedex.pokedex.repository.PokemonRepository;
import be.vermeer.pokedex.pokedex.repository.PokemonTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokemonRepository repository;

    @Mock
    private PokemonTypeRepository typeRepository;

    @InjectMocks
    private PokemonService service;

    @Test
     void findByIdReturnsPokemonWhenFound() {
        // arrange
        PokemonType electric = new PokemonType("Electric");
        Pokemon pokemon = new Pokemon("Pikachu", Set.of(electric));
        when(repository.findById(1)).thenReturn(Optional.of(pokemon));

        // act
        PokemonResponse response = service.findById(1);

        // assert
        assertEquals("Pikachu", response.name());
        assertEquals(1, response.types().size());
        assertEquals(
                "Electric",
                response.types().iterator().next().name()
        );
    }

    @Test
     void findByIdThrowsExceptionWhenNotFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());
        PokemonNotFoundException ex = assertThrows(PokemonNotFoundException.class, () -> service.findById(999));
        assertEquals("Pokemon 999 not found.", ex.getMessage());
    }

    @Test
    void createSavesAndReturnsPokemon() {
        CreatePokemonRequest request = new CreatePokemonRequest();
        request.setName("Pikachu");
        request.setTypeIds(Set.of(1));

        PokemonType electric = new PokemonType("Electric");
        Pokemon pokemon = new Pokemon("Pikachu", Set.of(electric));

        when(typeRepository.findAllById(Set.of(1))).thenReturn(List.of(electric));
        when(repository.save(any(Pokemon.class))).thenReturn(pokemon);

        PokemonResponse response = service.create(request);
        assertEquals("Pikachu", response.name());
        assertEquals("Electric",
                response.types().iterator().
                        next().name());

        ArgumentCaptor<Pokemon> captor = ArgumentCaptor.forClass(Pokemon.class);
        verify(repository).save(captor.capture());
        Pokemon savedPokemon = captor.getValue();

        assertEquals("Pikachu", savedPokemon.getName());
        assertEquals(1, savedPokemon.getTypes().size());
        assertEquals(
                "Electric",
                savedPokemon.getTypes().iterator().next().getName()
        );
    }

    @Test
    void createAndThrowExceptionWhenPokemonTypeNotFound() {
        CreatePokemonRequest request = new CreatePokemonRequest();
        request.setName("Pikachu");
        request.setTypeIds(Set.of(999));

        when(typeRepository.findAllById(Set.of(999))).thenReturn(List.of());

        PokemonTypeNotFoundException ex = assertThrows(
                PokemonTypeNotFoundException.class,
                () -> service.create(request)
        );
        assertEquals("One or more PokemonType IDs do not exist.", ex.getMessage());
        verify(repository, never()).save(any(Pokemon.class));
    }

    @Test
    void updateSavesAndReturnsPokemon() {
        PokemonType pokemonType = new PokemonType("Electric");
        Pokemon pokemon = new Pokemon("Pikachu", Set.of(pokemonType));
        UpdatePokemonRequest request = new UpdatePokemonRequest();
        String name = "Raichu";
        request.setName(name);

        when(repository.findById(1)).thenReturn(Optional.of(pokemon));
        when(repository.save(pokemon)).thenReturn(pokemon);

        PokemonResponse response = service.update(1, request);

        assertEquals("Raichu", response.name());
        assertEquals("Electric", response.types().iterator().next().name());
        verify(repository).save(pokemon);
        verifyNoInteractions(typeRepository);
    }

    @Test
    void deleteExistingPokemon() {
        PokemonType pokemonType = new PokemonType("Electric");
        Pokemon pokemon = new Pokemon("Pikachu", Set.of(pokemonType));
        when(repository.findById(1)).thenReturn(Optional.of(pokemon));
        service.delete(1);
        verify(repository).delete(pokemon);
    }

    @Test
    void deleteThrowsExceptionWhenNotFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());
        assertThrows(
                PokemonNotFoundException.class,
                () -> service.delete(999)
        );
        verify(repository, never()).delete(any(Pokemon.class));
    }
}
