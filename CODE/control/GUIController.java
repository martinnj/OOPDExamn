package control;

import model.GameManager;
import model.Player;

import javax.swing.JOptionPane;

/**
 * Controller that keeps the event loop going.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class GUIController
{
    private GameManager manager;
    
    /**
     * Creates a new controller.
     * @param   manager     a game manager
     */
    public GUIController(GameManager manager)
    {
        this.manager = manager;
    }
    
    /**
     * Runs the game by taking a turn until a player has won.
     * 
     * @throws InterruptedException
     */
    public void run()
    {
        while (!this.manager.getGame().gameOver())
        {
            try {
                Thread.sleep(80); // 12.5 fps ... ish
            }
            catch (InterruptedException e)
            {
                System.exit(0);
            }
            this.manager.takeTurn();
        }
        
        this.showGameResult();
    }
    
    /**
     * Shows a message box with the game result.
     */
    private void showGameResult()
    {
        JOptionPane.showMessageDialog(null, this.getResultMessage(), "Game result", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Gets a result message about the players.
     */
    private String getResultMessage()
    {
        Player player = this.manager.getWinner();
        
        if (player == null)
            return "The game was a tie!";
        
        return String.format("Winner: %s, score: %d", player.getName(), player.getScore());
    }
}
