package model;

import model.strategies.IStrategy;
import java.awt.Color;


/**
 * A computer player in the game of Tron. This computer is controlled by
 * the strategy specified. It is possible to change the strategy mid way.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @author Martin Nicklas Jørgensen
 * @version 1.1 (17-01-2011)
 */
public class ComputerPlayer extends Player
{    

    private IStrategy strategy;
    /**
     * Constructs a new computer player with the specified strategy, name and color
     * 
     * @param   color       The color of the player
     * @param   name        The name of the player
     * @param   strategy    The strategy of the player
     */
    public ComputerPlayer(Color color, String name, IStrategy strategy)
    {
        super(color, name);
        this.strategy = strategy;
    }
    
    /**
     * The direction of the next move this player wants to do.
     * This method does not perform the move, but only informs the game
     * that the player wants to do this move.
     * 
     * @param   game  The state of the game.
     * 
     * @return The direction of the wished move.
     */
    public Direction nextMove(Game game)
    {
        return strategy.nextMove(game, this);
    }
}
