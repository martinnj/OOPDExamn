package model.strategies;

import model.Direction;
import model.Position;
import model.ComputerPlayer;
import model.Board;
import model.Game;
import model.Player;

import java.awt.Color;

import java.util.ArrayList;

/**
 * The test class WallHuggerStrategyTest.
 *
 * @author  Martin Nicklas Jørgensen
 * @version 0.1 (20-01-2011)
 */
public class WallHuggerStrategyTest extends junit.framework.TestCase
{
    private ComputerPlayer player;
    private ArrayList<Player> players;
    private Board board;
    private Game game;
    /**
     * Default constructor for test class WallHuggerStrategyTest
     */
    public WallHuggerStrategyTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
        board = new Board(10, 10);
        player = new ComputerPlayer(Color.RED, "Player #1", new WallHuggerStrategy());
        player.setPosition(new Position(5,1));
        players = new ArrayList<Player>();
        players.add(player);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
        board = null;
        game = null;
        player = null;
        players = null;
    }

    /**
     * Test to make sure the Strategy will not run straight into a wall if its avoidable.
     */
    public void testAvoidAheadCollision()
    {
        player.setDirection(Direction.NORTH);                   // make player want to move north
        board.occupyField(new Position(5,0));                   // occupy the field straight ahead of the player.
        game = new Game(board, players);                        // create game instance
        assertNotSame(Direction.NORTH, player.nextMove(game));  // make sure the strategy doesn't move north (into the wall)
    }

    /**
     * Tests the the strategy turns to the correct direction when faced with a wall.
     */
    public void testWallAheadTurn()
    {
        player.setDirection(Direction.NORTH);                   // make player want to move north
        board.occupyField(new Position(5,0));                   // occupy the field straight ahead of the player.
        board.occupyField(new Position(6,0));                   // occupy the field to the right from the other wall
        game = new Game(board, players);                        // create game instance
        assertEquals(Direction.EAST, player.nextMove(game));    // test if the strategy takes it east along the wall
    }

    /**
     * Tests the strategys ability to follow a wall if it is already parallel.
     */
    public void testFollowWall()
    {
        player.setDirection(Direction.EAST);                     // make player want to move north
        board.occupyField(new Position(5,0));                    // occupy the field straight ahead of the player.
        board.occupyField(new Position(6,0));                    // occupy the field to the right from the other wall
        board.occupyField(new Position(7,0));                    // occupy the field to the right from the other wall
        game = new Game(board, players);                         // create game instance
        assertEquals(Direction.EAST , player.nextMove(game));    // test if the strategy takes it east along the wall

    }

    /**
     * Tests the strategy to see if it avoid crashig into things when turning.
     */
    public void testDontTurnToCrash()
    {
        player.setDirection(Direction.NORTH);                   // make player want to move north
        board.occupyField(new Position(5,0));                   // occupy the field straight ahead of the player.
        board.occupyField(new Position(6,0));                   // occupy the field to the right from the other wall
        board.occupyField(new Position(4,0));                   // occupy the field to the left from the other wall
        board.occupyField(new Position(4,1));                   // oocupy the field below the last wall to make a "corner"
        game = new Game(board, players);                        // create game instance
        assertEquals(Direction.EAST, player.nextMove(game));    // test if the strategy takes it east along the wall
    }
    
    /**
     * Tests that the strategy continues straight ahead if trapped.
     */
    public void testSuicideForward()
    {
        player.setDirection(Direction.NORTH);                   // make player want to move north
        board.occupyField(new Position(5,0));                   // occupy the field straight ahead of the player.
        board.occupyField(new Position(6,0));                   // occupy the field to the right from the other wall
        board.occupyField(new Position(6,1));                   // occupy the field bewlow the last wall to make a "curner"
        board.occupyField(new Position(4,0));                   // occupy the field to the left from the other wall
        board.occupyField(new Position(4,1));                   // oocupy the field below the last wall to make a "corner"
        board.occupyField(new Position(5,2));                   // occupy the field below the computer, it is now trapped.
        game = new Game(board, players);                        // create game instance
        assertEquals(Direction.NORTH, player.nextMove(game));    // test if the strategy takes it east along the wall
    }

    /**
     * 
     * 
     * NEXT MOVE SKAL HAVE game, player som arg!
     * GAME SKAL HAVE board, collection players som arg!
     * 
     * 
     */
}

