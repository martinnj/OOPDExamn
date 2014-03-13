package model;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Enumeration class Direction
 * 
 * 
 * @author Søren Dahlgaard
 * @author Daniel Hessellund Egeberg
 * @version 1
 */
public enum Direction
{   
    /**
     * West direction.
     */
    WEST {
        public Direction getOpposite()
        {
            return EAST;
        }
    },
    /**
     * North direction
     */
    NORTH {
        public Direction getOpposite()
        {
            return SOUTH;
        }
    },
    /**
     * East direction
     */
    EAST {
        public Direction getOpposite()
        {
            return WEST;
        }
    },
    /**
     * South direction
     */
    SOUTH {
        public Direction getOpposite()
        {
            return NORTH;
        }
    };
    
    /**
     * Retrieves the direction opposite to this one
     * 
     * @return  The opposite direction
     */
    abstract public Direction getOpposite();
    
    /**
     * Returns the directions in random order
     * 
     * @return A collection containing the directions in random order.
     */
    public static Collection<Direction> shuffledValues()
    {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (Direction d : Direction.values())
            directions.add(d);
        Collections.shuffle(directions);
        
        return directions;
    }
}
