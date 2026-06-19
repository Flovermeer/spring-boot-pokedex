package be.vermeer.pokedex.pokedex.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(int id) {
        super("Pokemon " + id + " not found.");
    }
}
