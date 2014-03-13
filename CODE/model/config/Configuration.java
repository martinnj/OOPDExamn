package model.config;

import java.util.Collection;
import model.Position;


/**
 * Configuration of a Tron game. This class specifies the size of the board,
 * the position of walls on the board, the scale factor for drawing and the
 * collection of start positions on the board.
 * 
 * @author Søren Dahlgaard
 * @author Daniel Hessellund Egeberg
 * @version 1
 */
public class Configuration
{
    private int width, height, scaleFactor;
    private Collection<LineSegment> lineSegments;
    private Collection<Position> startPositions;

    /**
     * Constructs a new configuration with the specified parameters.
     * 
     * @param   width           The width of the board
     * @param   height          The height of the board
     * @param   scaleFactor     The scale factor to be used for drawing the board
     * @param   lineSegments    A collection of walls on the board
     * @param   startPositions  A collection of positions where players can start.
     */
    public Configuration(int width, int height, int scaleFactor,
                         Collection<LineSegment> lineSegments,
                         Collection<Position> startPositions)
    {
        this.width          = width;
        this.height         = height;
        this.scaleFactor    = scaleFactor;
        this.lineSegments   = lineSegments;
        this.startPositions = startPositions;
    }

    /**
     * Gets the width of the board that the configuration represents
     * 
     * @return  Width of the board
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     * Gets the height of the board that the configuration represents
     * 
     * @return  Height of the board
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * Retrieves the scale factor of the board for drawing
     * 
     * @return  The scale factor
     */
    public int getScaleFactor()
    {
        return scaleFactor;
    }
    
    /**
     * Retrieves the collection of walls on the board.
     * 
     * @return The collection of walls
     */
    public Collection<LineSegment> getLineSegments()
    {
        return lineSegments;
    }
    
    /**
     * Retrieves the collection of start positions on the board.
     * 
     * @return The collection of start positions.
     */
    public Collection<Position> getStartPositions()
    {
        return startPositions;
    }
}
