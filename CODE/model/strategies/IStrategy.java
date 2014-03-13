package model.strategies;

import model.Direction;
import model.Game;
import model.Player;
/**
 * An interface used for strategies.
 * 
 * @author Martin Nicklas Jørgensen
 * @version 0.1 (17-01-2011)
 */
public interface IStrategy
{
    /**
     * Decides what direction the player should move based on the player position and the state of the game.
     * @param game A Game-state that contains the information needed for tactical decisions.
     * @param player A Player object that contains the player information.
     * @return A Direction with the move to make.
     */
    public abstract Direction nextMove(Game game, Player player);
}
