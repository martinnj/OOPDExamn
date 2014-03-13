package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import javax.swing.JFrame;
import java.awt.Container;

import model.Direction;
import model.HumanPlayer;


/**
 * Main window for the application.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @author Martin Nicklas Jørgensen
 * @version 1.1 (19-01-2011)
 */
public class MainWindowFrame extends JFrame
{
    private GameInput gameInput;
    private JFrame frame;
    private Container contentPane;
    
    /**
     * Creates a new main window with an image panel.
     * 
     * @param   tronDisplayPanel  the game display panel
     */
    public MainWindowFrame(TronDisplayPanel tronDisplayPanel)
    {
        frame = new JFrame("Tron Game : OOPD examn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = frame.getContentPane();
        gameInput = new GameInput();
        
        contentPane.add(tronDisplayPanel);
        frame.addKeyListener(gameInput);
                
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Adds a HumanPllayer as observer on the gameInput class.
     * Used for getting keyboard input to player 1.
     * 
     * @param   player  The HumanPlayer to add as observer.
     */
    public void addHumanObserver(HumanPlayer player)
    {
        gameInput.addObserver(player);
    }
    
    /**
     * A class for listening to the keyboard for arrow key strokes.
     * Notifies observers with a direction corresponding to the key pressed.
     */
    private class GameInput extends Observable implements KeyListener
    {
        /**
         * Handles the key typed event.
         * 
         * Nothing happens.
         * 
         * @param e the key event.
         */
        @Override
        public void keyTyped(KeyEvent e)
        {
            //No intended behaviour.
        }
        
        /**
         * Handles the key released event.
         * 
         * Nothing happens.
         * 
         * @param e the key event.
         */
        @Override
        public void keyReleased(KeyEvent e)
        {
            //No intended behaviour.
        }
    
        /**
         * Handles the key pressed event.
         * 
         * When game keys are used, the observers are notified.
         * 
         * @param e the key event.
         */
        @Override
        public void keyPressed(KeyEvent e)
        {
            Direction direction = null;
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_DOWN:
                    direction = Direction.SOUTH;
                    break;
                case KeyEvent.VK_LEFT:
                    direction = Direction.WEST;
                    break;
                case KeyEvent.VK_UP:
                    direction = Direction.NORTH;
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = Direction.EAST;
                    break;
            }
            
            if (direction != null)
            {
                setChanged();
                notifyObservers(direction);
            }
        }
    }
}
