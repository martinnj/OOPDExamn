package model;

import java.util.Collection;
import java.util.ArrayList;

/**
 * A class representing a position or position on the Tron board.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Lars Christian Thoudahl
 * @author Søren Dahlgaard
 * @author Martin Nicklas Jørgensen
 * @version 1.2 (17-01-2011)
 */
public class Position
{
    private int x;
    private int y;

    /**
     * Constructor for the Position class.
     * @param x An integer value the will become the positions x-coordinate.
     * @param y An integer value the will become the positions y-coordinate.
     */
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the neighboring coordinate in a specific direction.
     * @param direction The direction enum of the direction we want to get the neighbour from.
     * @return A new position with the correct coordinates for the neightbour. Returns null if an invalid direction is given.
     */
    public Position getNeighbour(Direction direction)
    {
        Position output;
        switch (direction) {
            case NORTH : output = new Position(getX(), getY() - 1); break;
            case WEST : output = new Position(getX() - 1, getY()); break;
            case EAST : output = new Position(getX() + 1, getY()); break;
            case SOUTH : output = new Position(getX(), getY() + 1); break;
            default : output = null; break;
        }
        return output;
    }

    /**
     * Returns the x value of the positions coordinate-set.
     * @return An integer with the x-coordinate.
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Returns the y value of the positions coordinate-set.
     * @return An integer with the y-coordinate.
     */
    public int getY()
    {
        return this.y;
    }
    
    /**
     * A collection of the positions neighbouring this position. Note that only the positions
     * to the west, east, north and south are neighbours. Positions diagonal from this
     * are not.
     * 
     * @return  A collection of the neighbouring positions
     */
    public Collection<Position> getNeighbours()
    {
        ArrayList<Position> neighbours = new ArrayList<Position>();
        for (Direction dir : Direction.values())
            neighbours.add(getNeighbour(dir));

        return neighbours;
    }
}
