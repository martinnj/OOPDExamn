package model.strategies;

import model.Position;
import model.Game;
import model.Player;
import model.Direction;

import java.util.ArrayList;
/**
 * A strategy that attempts to move onto a position that is 
 * adjacent to a wall.
 * 
 * Version 1.1 fix:
 * - Removed suicidal tendencies.
 * 
 * Version 1.2 update:
 * - Made it scout neighbours for adjecent walls.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Martin Nicklas Jørgensen
 * @version 1.2 (17-01-2011)
 */
public class WallHuggerStrategy implements IStrategy
{   
    private ArrayList<Direction> directions;

    /**
     * Constructor for the wallhugger strategy.
     * The directions arraylist are initialized to a shuffled order to make different bots behave different (for fun and show.)
     */
    public WallHuggerStrategy()
    {
        directions = new ArrayList<Direction>(Direction.shuffledValues());
    }

    /**
     * Decides what direction the player should move based on the player position and the state of the game.
     * @param game A Game-state that contains the information needed for tactical decisions.
     * @param player A Player object that contains the player information.
     * @return A Direction with the move to make.
     */
    public Direction nextMove(Game game, Player player)
    {
        Position playerPosition = player.getPosition();
        Direction playerDirection = player.getDirection();

        // look for a wall.
        for(Direction dir : directions)
        {
            // get the direction of the wall. (if any.)
            if(game.isOccupied(playerPosition.getNeighbour(dir))) {
                Position wallPosition = playerPosition.getNeighbour(dir);
                for(Direction innerDir : directions)
                {
                    if(game.isOccupied(wallPosition.getNeighbour(innerDir)) && !game.isOccupied(playerPosition.getNeighbour(innerDir))) {
                        return innerDir;
                    }
                }
            }
            // scout neighbours for adjecent walls then move there.
            else {
                for(Direction innerDir : directions) {
                    Position neighbour = playerPosition.getNeighbour(innerDir);
                    
                    for(Direction innerDir2 : directions) {
                        if (!game.isOccupied(neighbour) && game.isOccupied(neighbour.getNeighbour(innerDir2)) && !game.isOccupied(playerPosition.getNeighbour(innerDir2))) {
                            return innerDir2;
                        }
                    }
                }
            }
        }

        // find free path if nothing else is possible.
        for(Direction dir : directions)
        {
            if(!game.isOccupied(playerPosition.getNeighbour(dir))) {
                return dir;
            }
        }

        // if there is no free path, just continue ahead to doom, and cake.
        return playerDirection;
    }
}
