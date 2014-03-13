package model.config;

import model.Position;
import java.util.Collection;
import java.util.ArrayList;


/**
 * Class for representing a line segment.
 * 
 * @author Daniel Hessellund Egeberg
 * @author SÃøren Dahlgaard
 * @version 1
 */
public class LineSegment
{
    private Position p1, p2;

    /**
     * Constructor for objects of class LineSegment
     * 
     * @param   p1  One endposition of the line segment
     * @param   p2  The other endposition of the line segment
     */
    public LineSegment(Position p1, Position p2)
    {
        this.p1 = p1;
        this.p2 = p2;
    }
    
    /**
     * Constructs a new LineSegment with the specified endpositions
     * 
     * @param   x1  X-coordinate of the first endposition of the segment.
     * @param   y1  Y-coordinate of the first endposition of the segment.
     * @param   x2  X-coordinate of the second endposition of the segment.
     * @param   y2  Y-coordinate of the second endposition of the segment.
     */
    public LineSegment(int x1, int y1, int x2, int y2)
    {
        this(new Position(x1, y1), new Position(x2, y2));
    }
    
    /**
     * Retrieves the first position.
     * 
     * @return The first position
     */
    public Position getP1()
    {
        return p1;
    }
    
    /**
     * Retrieves the second position.
     * 
     * @return The second position
     */
    public Position getP2()
    {
        return p2;
    }
    
    /**
     * Retrieves a collection of the positions that form the line segment.
     * This uses Bresenham's line algorithm
     * The algorithm is described in more detail at: http://en.wikipedia.org/wiki/Bresenham's_line_algorithm
     * 
     * @return  A collection of the position that form the segment.
     */    
    public Collection<Position> getPositions()
    {
        int temp; //used for swapping.
        ArrayList<Position> positions = new ArrayList<Position>();
        
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();
        
        // Bresenham's algorithm only works on lines going left to right with a slope less than one
        // We therefore swap around the coordinates to account for this.
        boolean steep = Math.abs(y2 - y1) > Math.abs(x2 - x1);
        if (steep) // Swap x and y-coordinate if the slope is bigger than 1.
        {
            temp = x1;
            x1 = y1;
            y1 = temp;
            
            temp = x2;
            x2 = y2;
            y2 = temp;
        }
        if (x1 > x2) // Swap the endpositions if the line goes from right to left.
        {
            temp = x1;
            x1 = x2;
            x2 = temp;
            
            temp = y1;
            y1 = y2;
            y2 = temp;
        }
        
        int deltax = x2 - x1;
        int deltay = Math.abs(y2 - y1);
        int error  = deltax >> 1;
        
        int ystep  = y1 < y2 ? 1 : -1;
        int y      = y1;
        
        for (int x = x1; x <= x2; ++x)
        {
            if (steep) // We swapped x and y so need to swap them back.
                positions.add(new Position(y, x));
            else
                positions.add(new Position(x, y));
            
            error -= deltay;
            if (error < 0)
            {
                y += ystep;
                error += deltax;
            }
        }
        return positions;
    }
}
