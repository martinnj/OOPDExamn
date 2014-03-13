package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.ArrayList;

import model.config.Configuration;
import model.config.LineSegment;

/**
 * A class for managing a game of Tron.
 * 
 * @author  Søren Dahlgaard
 * @author  Daniel Hessellund Egeberg
 * @author  Martin Nicklas Jørgensen
 * @version 1.1 (18-01-2011)
 */
public class GameManager extends Observable
{
    private Board board;
    private Collection<Player> players;
    private Game  game;

    /**
     * Constructs a new game manager from a configuration and a collection of players
     * 
     * A new GameManager object creates a Board object for the game and assigns the
     * players to their respective start locations.
     * 
     * @param   config  The configuration of the new game
     * @param   players The players that will be competing in the game
     */
    public GameManager(Configuration config, Collection<Player> players)
    {        
        this.players = players;
        this.createBoard(config);

        this.game = new Game(board, players);
    }

    /**
     * Performs a move for each player. Makes sure noone is making a backwards move or moves into a wall.
     * Also makes sure each visited field gets converted to a wall.
     */
    public void takeTurn()
    {
        for(Player player : players) {
            if(player.isAlive()) {
                Direction wantedMove = player.nextMove(game);
                if (game.isOccupied(player.getPosition().getNeighbour(wantedMove))) {
                    player.kill();
                    board.occupyField(player.getPosition());
                }
                else if(wantedMove == player.getDirection().getOpposite()) {
                    player.performMove(player.getDirection());
                    board.occupyField(player.getPosition());
                    board.occupyField(player.getPreviousPosition());
                }
                else {
                    player.performMove(player.nextMove(game));
                    board.occupyField(player.getPosition());
                    board.occupyField(player.getPreviousPosition());
                }
            }
        }
        setChanged();
        notifyObservers(players);
    }

    /**
     * Retrieves the winner of the game or null if the game was a tie.
     * 
     * @return The winner or null in case of a tie
     */
    public Player getWinner()
    {
        for (Player player : this.players)
            if (player.isAlive())
                return player;

        return null;
    }

    /**
     * Retrieves the immutable game object representing the status of the game.
     * 
     * @return The game object
     */
    public Game getGame()
    {
        return this.game;
    }

    /**
     * Returns the player list
     * 
     * @return the player list
     */
    public Collection<Player> getPlayers()
    {
        return this.players;
    }

    /**
     * Creates the board from a selected Configuration instance. This operation is making board sizes building walls and assigning positions to players.
     * @param   config  Configuration instance to use for creating the level.
     */
    private void createBoard(Configuration config)
    {
        board = new Board(config.getWidth(), config.getHeight());

        // add walls
        ArrayList<LineSegment> lines = new ArrayList<LineSegment>(config.getLineSegments());
        // make sure edges are also added
        lines.add(new LineSegment(0, 0, 0, config.getHeight()-1));
        lines.add(new LineSegment(0, 0, config.getWidth()-1, 0));
        lines.add(new LineSegment(config.getWidth()-1, config.getHeight()-1, config.getWidth()-1, 0));
        lines.add(new LineSegment(config.getWidth()-1, config.getHeight()-1, 0, config.getHeight()-1));

        for (LineSegment line : lines) {
            for (Position position : line.getPositions()) {
                board.occupyField(position);
            }
        }

        // assign a start position to each player
        // note that of there is fewer players than position it won't break, 
        // because it loops though players and then fetches a position.
        int posIndex = 0;
        ArrayList<Position> positions = new ArrayList<Position>(config.getStartPositions());
        for(Player player : players) {
            player.setPosition(positions.get(posIndex));
            posIndex++;
        }
    }
}
