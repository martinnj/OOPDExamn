package model.config;


/**
 * Interface for a parser of a Tron configuration.
 * 
 * @author Søren Dahlgaard
 * @author Daniel Hessellund Egeberg
 * @version 1
 */
public interface IParser
{
    /**
     * Performs the parsing and returns the configuration.
     * 
     * @throws  ParserException On malformed input
     * @return  A Configuration object resulting from the parsed data
     */
    public Configuration getConfiguration() throws ParserException;
}
