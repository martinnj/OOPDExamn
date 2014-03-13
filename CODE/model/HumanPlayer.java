package model;

import java.awt.Color;
import java.util.Observer;
import java.util.Observable;


/**
 * A human player in the tron game.
 * This player is controlled through keyboard input.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class HumanPlayer extends Player implements Observer
{
    private Direction nextDirection;
    
    /**
     * Constructs a new human player with the specified color and name
     * 
     * @param   color   The color of the player
     * @param   name    The name of the player
     */
    public HumanPlayer(Color color, String name)
    {
        super(color, name);
    }

    /**
     * The wished direction of the next move.
     * This is set through keyboard input.
     * 
     * @param   board   The board of the current game
     */
    public Direction nextMove(Game game)
    {
        return nextDirection == null ? Direction.WEST : nextDirection;
    }
    
    /**
     * Updates the next direction of this player if the argument is a
     * Direction.
     * 
     * @param   o   The object invoking this method
     * @param   arg The argument sent. If this is a Direction the next move is changed.
     */
    public void update(Observable o, Object arg)
    {
        if (arg instanceof Direction)
            nextDirection = (Direction)arg;
    }
}
