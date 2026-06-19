package be.vermeer.pokedex.pokedex.exception;

public class PokemonTypeNotFoundException extends RuntimeException {
    public PokemonTypeNotFoundException() {
        super("One or more PokemonType IDs do not exist.");
    }
}
