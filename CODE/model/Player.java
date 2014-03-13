package model;

import java.awt.Color;


/**
 * Abstract class Player
 * Represents a player in the tron game. A player has a name, position,
 * color and direction. Also he might be alive or dead depending on the
 * state of the game.
 * 
 * @author Søren Dahlgaard
 * @author Daniel Hessellund Egeberg
 * @author Martin Nicklas Jørgensen
 * @version 1.1 (17-01-2011)
 */
public abstract class Player
{
    private Position    position;
    private Direction   direction;
    private Color       color;
    private String      name;
    private boolean     alive;
    private int         score;

    /**
     * Creates a new Player object with the given color and name.
     * The player is alive and has no position/direction on the board -
     * This is specified when the game starts.
     * 
     * @param   color   The color of the player. This is used for drawing.
     * @param   name    The name of the player.
     */
    public Player(Color color, String name)
    {
        this.position   = null;
        this.color      = color;
        this.name       = name;
        this.direction  = null; //This is set when the player makes a move.
        this.alive      = true;
        this.score      = 0;
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
    abstract public Direction nextMove(Game game);
    
    /**
     * Performs a move in a given direction.
     * This changes the direction and the position of the player.
     * 
     * @param   direction   the direction to move.
     * @throws IllegalStateException if called on a dead player
     */
    public void performMove(Direction direction)
    {
        if (!alive)
            throw new IllegalStateException("Player is not alive");
        
        this.direction  = direction;
        this.position   = position.getNeighbour(direction);
        this.score++;
    }
    
    /**
     * Retrieves the score of this player.
     * 
     * @return the score
     */
    public int getScore()
    {
        return this.score;
    }
    
    /**
     * Whether the player is alive or not
     * 
     * @return true if the player is alive, false otherwise.
     */
    public boolean isAlive()
    {
        return this.alive;
    }
    
    /**
     * Kills the player. This ensures that isAlive() returns false.
     */
    public void kill()
    {
        this.alive = false;
    }
    
    /**
     * Retrieves the previous position of the player
     * 
     * @return  The previous position.
     */
    public Position getPreviousPosition()
    {
        if (direction == null)
            return null;
            
        return position.getNeighbour(direction.getOpposite());
    }
    
    /**
     * Sets the position of the player.
     * 
     * @param   position    The new position of the player.
     */
    public void setPosition(Position position)
    {
        this.position = position;
    }
    
    /**
     * Gets the position of the player
     * 
     * @return  The current position of the player.
     */
    public Position getPosition()
    {
        return this.position;
    }
    
    /**
     * Gets the direction the player is currently moving
     * 
     * @return  The direction the player is moving
     */
    public Direction getDirection()
    {
        return this.direction;
    }
    
    /**
     * Gets the color of the player
     * 
     * @return this player's color
     */
    public Color getColor()
    {
        return this.color;
    }
    
    /**
     * Gets the name of the player
     * 
     * @return this player's name
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Sets the direction of the player. Should only be used when initialising each player to a direction.
     * @param   newDirection A direction to face.
     */
    public void setDirection(Direction newDirection) {
        this.direction = newDirection;
    }
}
