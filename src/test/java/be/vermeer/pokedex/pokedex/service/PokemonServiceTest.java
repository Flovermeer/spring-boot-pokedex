package be.vermeer.pokedex.pokedex.service;

import be.vermeer.pokedex.pokedex.dto.CreatePokemonRequest;
import be.vermeer.pokedex.pokedex.dto.PokemonResponse;
import be.vermeer.pokedex.pokedex.dto.UpdatePokemonRequest;
import be.vermeer.pokedex.pokedex.exception.PokemonNotFoundException;
import be.vermeer.pokedex.pokedex.model.Pokemon;
import be.vermeer.pokedex.pokedex.repository.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokemonRepository repository;

    @InjectMocks
    private PokemonService service;

    @Test
     void findByIdReturnsPokemonWhenFound() {
        // arrange
        Pokemon pokemon = new Pokemon("Pikachu", "Electric");
        when(repository.findById(1)).thenReturn(Optional.of(pokemon));

        // act
        PokemonResponse response = service.findById(1);

        // assert
        assertEquals(pokemon.getName(), response.name());
        assertEquals(pokemon.getType(), response.type());
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
        String name = "Pikachu";
        String type = "Electric";

        request.setName(name);
        request.setType(type);
        Pokemon pokemon = new Pokemon(name, type);

        when(repository.save(any(Pokemon.class))).thenReturn(pokemon);
        PokemonResponse response = service.create(request);
        assertEquals(name, response.name());
        assertEquals(type, response.type());

        ArgumentCaptor<Pokemon> captor = ArgumentCaptor.forClass(Pokemon.class);
        verify(repository).save(captor.capture());
        Pokemon savedPokemon = captor.getValue();

        assertEquals(name, savedPokemon.getName());
        assertEquals(type, savedPokemon.getType());
    }

    @Test
    void updateSavesAndReturnsPokemon() {
        Pokemon pokemon = new Pokemon("Pikachu", "Electric");
        UpdatePokemonRequest request = new UpdatePokemonRequest();
        String name = "Raichu";
        request.setName(name);
        when(repository.findById(1)).thenReturn(Optional.of(pokemon));

        PokemonResponse response = service.update(1, request);

        assertEquals("Raichu", response.name());
        assertEquals("Electric", response.type());
        verify(repository).save(pokemon);
    }

    @Test
    void deleteExistingPokemon() {
        Pokemon pokemon = new Pokemon("Pikachu", "Electric");
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
