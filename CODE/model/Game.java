package model;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Write a description of class Game here.
 * 
 * @author Christian Thoudahl
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class Game
{
    private Board board;
    private Collection<Player> players;

    /**
     * Constructor for objects of class Game
     * 
     * @param   board   The board of the game
     * @param   players The players of the game
     */
    public Game(Board board, Collection<Player> players)
    {
        this.board   = board;
        this.players = players;
    }
 
    /**
     * Whether the game is over or not.
     * 
     * @return  true if there is 1 or 0 players alive. False otherwise
     */
    public boolean gameOver()
    {
        int alive = 0;
        for (Player player : players)
        {
            if (player.isAlive())
                alive++;
        }
        return alive <= 1;
    }
    
    /**
     * The width of the game's board
     * 
     * @return The width
     */
    public int getWidth()
    {
        return board.getWidth();
    }
    
    /**
     * The height of the game's board
     * 
     * @return The height
     */
    public int getHeight()
    {
        return board.getHeight();
    }
    
    /**
     * Whether a field on the board is occupied or not
     * 
     * @param   position    The position to check
     * 
     * @return  true if the position is occupied, false otherwise
     */
    public boolean isOccupied(Position position)
    {
        return board.isOccupied(position);
    }
    
    /**
     * Returns a collection of all the different players' positions.
     * 
     * @return  A collection of the players' positions.
     */
    public Collection<Position> getPlayerPositions()
    {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (Player player : players) {
            positions.add(player.getPosition());
        }
        return positions;
    }
}
