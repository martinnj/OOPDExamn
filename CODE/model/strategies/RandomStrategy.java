package model.strategies;

import java.util.ArrayList;

import model.Game;
import model.Player;
import model.Direction;
import model.Board;
import model.Position;
/**
 * A simple strategy that choses a random, valid direction.
 * 
 * @author Søren Dahlgaard
 * @author Daniel Hessellund Egeberg
 * @author Martin Nicklas Jørgensen
 * @version 1.0 (17-01-2011)
 */
public class RandomStrategy implements IStrategy
{   
    /**
     * Constructor for the RandomStrategy class.
     */
    public RandomStrategy()
    {
    }
    
    /**
     * Decides where to move next, the move is not based on any good intel but is just a random direction that will not result in suicide, unless the player is trapped.
     * @param   game    Instance of the game.
     * @param   player  Instance of the player to choose a move from.
     * @return  A direction the player should move.
     */
    public Direction nextMove(Game game, Player player)
    {
        ArrayList<Direction> directions = new ArrayList<Direction>(Direction.shuffledValues());
        Position playerPosition = player.getPosition();
        
        // go through all the directions to see if one of them is free.
        for(Direction dir : directions) {
            if(!game.isOccupied(playerPosition.getNeighbour(dir))) {
                return dir;
            }
        }
        
        // if none of the directions are free, continue straight ahead.
        return player.getDirection();
    }
}
