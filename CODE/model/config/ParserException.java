package model.config;


/**
 * Exception used by the IParser interface and implementing classes
 * 
 * @author Søren Dahlgaard
 * @author Daniel Hessellund Egeberg
 * @version 1
 */
public class ParserException extends Exception
{
	/**
     * Constructs a new ParserException with the specified message
     * 
     * @param   message Description of why the exception happened.
     */
    public ParserException(String message)
    {
        super(message);
    }
}
