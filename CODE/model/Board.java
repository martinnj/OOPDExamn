package model;

import java.util.ArrayList;

/**
 * A class for modeling the board of a Tron game.
 * The board is of size getWidth() x getHeight() and has some obstacles on it.
 * You can check if there's an obstacle at position x,y by calling isOccupied(x,y).
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @author Martin Nicklas Jørgensen
 * @version 1.1 (17-01-2011)
 */
public class Board
{
    private int width;
    private int height;
    private ArrayList<Position> occupiedPositions;
    /**
     * Constructs a new board with the specified width and height.
     * 
     * @param   width   the width of the board
     * @param   height  the height of the board
     */
    public Board(int width, int height)
    {
        this.height = height;
        this.width = width;
        this.occupiedPositions = new ArrayList<Position>();
    }

    /**
     * Returns the width of the board.
     * @return An integer value with the width of the board.
     */
    public int getWidth()
    {
        return this.width;
    }

    /**
     * Returns the height of the board.
     * @return An integer value with the height of the board.
     */
    public int getHeight()
    {
        return this.height;
    }

    /**
     * Sets a position as being occupied in the game.
     * @param position The position to occupy.
     */
    public void occupyField(Position position)
    {
        occupiedPositions.add(position);
    }

    /**
     * Checks if a given position is occupied.
     * @param position The position that need be checked for occupation.
     * @return A boolean value that shows the occupation of the position. True if the position is occupied, false if it is not.
     */
    public boolean isOccupied(Position position)
    {
        int x = position.getX();
        int y = position.getY();
        
        for(Position pos : occupiedPositions) {
            if(pos.getX() == x && pos.getY() == y) {
                return true;
            }
        }
        
        return false;
    }
}
