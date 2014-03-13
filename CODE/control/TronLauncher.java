package control;

import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.util.ArrayList;

import view.NewGameDialog;
import view.MainWindowFrame;
import view.TronDisplayPanel;

import model.config.Configuration;
import model.GameManager;
import model.Game;
import model.Player;
import model.HumanPlayer;
/**
 * Creates the GUI elements, the controller and launches the game.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @author Martin Nicklas Jørgensen
 * @version 1.1 (19-01-2011)
 */
public class TronLauncher
{
    /**
     * Main entry point for the application.
     * Brings all the parts together.
     */
    public static void main(String[] args)
    {

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception _) { } // Just ignore this.

        try {
            
            NewGameDialog ngd = new NewGameDialog(null,true);
            while(!ngd.isReady()) {
                // just wait around until dialog is finished and made ready ^_^
                // i tried without the loop, but the thread didn't get locked, so i made this ad-hoc solution.
            }
            Configuration config = ngd.getConfig();
            ArrayList<Player> players = new ArrayList<Player>(ngd.getPlayers());
            HumanPlayer human = ngd.getHuman();
            
            GameManager gm = ngd.getGameManager();
            TronDisplayPanel tdp = new TronDisplayPanel(config.getWidth(), config.getHeight(), config.getScaleFactor());
            gm.addObserver(tdp);
            Game game = gm.getGame();
            
            // IMPORTANT:
            // For some reason the game often stalls or goes very slowly when passing through the init method!
            // I have been unable to pinpoint a solution, but it seems to go faster when in debugging mode(stepping).
            tdp.init(game, players); 
            
            MainWindowFrame mainFrame = new MainWindowFrame(tdp);
            if (human != null) {
                mainFrame.addHumanObserver(human);
            }
            
            GUIController GUIc = new GUIController(gm);
            
            GUIc.run();

        }
        catch (Exception ex) {
           JOptionPane.showMessageDialog(null, "Error in TRON-game: " + ex.getMessage());
        }
    }
}
