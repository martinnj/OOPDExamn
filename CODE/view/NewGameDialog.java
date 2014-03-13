package view;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.ComputerPlayer;
import model.HumanPlayer;
import model.Player;
import model.Position;
import model.Direction;
import model.GameManager;

import model.config.Configuration;
import model.config.ParserException;
import model.config.TextParser;

import model.strategies.IStrategy;
import model.strategies.StrategyFactory;
import model.strategies.RandomStrategy;
import model.strategies.WallHuggerStrategy;

/**
 * Sets up a new game configuration.
 * 
 * @author Christian Thoudahl
 * @author Martin Nicklas Jørgensen
 * @version 1.2 (20-01-2011)
 */
public class NewGameDialog extends JDialog implements ActionListener
{
    private Configuration config;
    private GameManager gameManager;
    private HumanPlayer human;
    private ArrayList<Player> players;
    private File tronFile;
    private TextParser parser;
    private boolean ready;

    private JFrame frame;
    private Container contentPane;
    private JCheckBox humanCheck;
    private JFileChooser fc;
    private PlayerPanel player1;
    private JPanel playersPanel;
    private ArrayList<PlayerPanel> playerPanelList;

    /**
     * Constructor for objects of class NewGameDialog
     * 
     * @param   owner   The owner of the dialog
     * @param   modal   Specifies whether dialog blocks user input to other top-level windows when shown.
     *                  Also blocks the current thread until the dialog is disposed.
     */
    public NewGameDialog(JFrame owner, boolean modal)
    {
        super(owner, modal);
        ready = false;
        player1 = null;

        frame = new JFrame("Tron - New game setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("New game setup");
        contentPane.add(titleLabel,BorderLayout.NORTH);

        playerPanelList = new ArrayList<PlayerPanel>();
        players = new ArrayList<Player>();
        playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.PAGE_AXIS));
        useFileChooser();

        JButton okButton = new JButton("OK! LETS GO!");
        okButton.addActionListener(this);
        contentPane.add(okButton,BorderLayout.SOUTH);

        frame.pack();
        int width = frame.getWidth();
        int height = frame.getHeight();
        width = 285;
        frame.setSize(width,height + 63);
        frame.setVisible(true);

    }

    /**
     * Shows a file chooser, and if it is satisfied with the file it will generate a good interface, else it will make a button for showing the file chooser again.
     */
    private void useFileChooser()
    {
        fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tron game files (*.tron)", "tron");
        fc.addChoosableFileFilter(filter);
        fc.setDialogTitle("Please select a Tron game file...");

        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            tronFile = fc.getSelectedFile();

            try {
                parser = new TextParser(tronFile);
                config = parser.getConfiguration();

                humanCheck = new JCheckBox("Use human player?" , false);
                humanCheck.addActionListener(this);
                playersPanel.add(humanCheck);

                Color color[] = TronColors.getColors(config.getStartPositions().size());
                int colorIndex = 0;
                for(Position position : config.getStartPositions()) {
                    if(colorIndex == 0) {
                        player1 = new PlayerPanel(color[colorIndex], "Player #" + (colorIndex + 1));
                        playersPanel.add(player1);
                    }
                    else {
                        PlayerPanel pPanel = new PlayerPanel(color[colorIndex], "Player #" + (colorIndex + 1));
                        playersPanel.add(pPanel);
                        playerPanelList.add(pPanel);
                    }
                    colorIndex++;
                }

                JButton openButton = new JButton("Open config file");
                openButton.addActionListener(this);
                playersPanel.add(openButton);	
            }
            catch (ParserException ex) {
                JOptionPane.showMessageDialog(this, "An error occured while parsing the file:\n" + ex.getMessage());
                JButton openButton = new JButton("Open config file");
                openButton.addActionListener(this);
                playersPanel.add(openButton);
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "The parser could not be created:\n" + ex.getMessage());
                JButton openButton = new JButton("Open config file");
                openButton.addActionListener(this);
                playersPanel.add(openButton);
            }
        }
        else {
            JButton openButton = new JButton("Open config file");
            openButton.addActionListener(this);
            playersPanel.add(openButton);
        }

        contentPane.add(playersPanel,BorderLayout.CENTER);
    }

    /**
     * Returns the configuration loaded through this dialog
     * 
     * @return  The configuration
     */
    public Configuration getConfig()
    {
        return this.config;
    }

    /**
     * Returns a collection of the players created with this dialog
     * 
     * @retun   The collection of players
     */
    public Collection<Player> getPlayers()
    {
        return this.players;
    }

    /**
     * Returns the human player if one has been selected by the user. If the user wishes a pure AI game
     * this returns null
     * 
     * @return  The HumanPlayer object associated with this game or null.
     */
    public HumanPlayer getHuman()
    {
        return this.human;
    }

    /**
     * Performs the needed actions for each control that gets activated (if the control is also linked to the actionListener.)
     * @param   e   The ActionEvent.
     */
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("Use human player?")) {
            player1.setComboBoxEnabled(!humanCheck.isSelected());
        }
        else if (command.equals("Open config file")) {
            playersPanel.removeAll();
            playerPanelList.clear();
            useFileChooser();
            frame.pack();
            int width = frame.getWidth();
            int height = frame.getHeight();
            width = 285;
            frame.setSize(width,height + 63);
            frame.setVisible(true);
        }
        else if(command.equals("OK! LETS GO!") && minimumPlayers()) {

            if (humanCheck.isSelected()) {
                human = new HumanPlayer(player1.getColor(), player1.getName());
                players.add(human);
            }
            else {
                switch(player1.getStrategyIndex()) {
                    case 0:
                    break;
                    case 1: 
                    players.add(new ComputerPlayer(player1.getColor(), player1.getName(), new WallHuggerStrategy()));
                    break;
                    case 2: 
                    players.add(new ComputerPlayer(player1.getColor(), player1.getName(), new RandomStrategy()));
                    break;
                    default:
                    break;
                }
            }

            for(PlayerPanel playerPanel : playerPanelList) {
                switch(playerPanel.getStrategyIndex()) {
                    case 0:
                    break;
                    case 1: 
                    players.add(new ComputerPlayer(playerPanel.getColor(), playerPanel.getName(), new WallHuggerStrategy()));
                    break;
                    case 2: 
                    players.add(new ComputerPlayer(playerPanel.getColor(), playerPanel.getName(), new RandomStrategy()));
                    break;
                    default:
                    break;
                }
            }

            for (Player player : players) {
                ArrayList<Direction> dirs = new ArrayList<Direction>(Direction.shuffledValues());
                player.setDirection(dirs.get(0));
            }

            gameManager = new GameManager(config, players);
            ready = true;
            frame.setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "Please activate 2 or more players.(Human & computer.)\nAn AI is activated once a strategy is selected from the dropdown.");
        }

    }
    
    /**
     * Decides whether there is enough players to start a game.
     */
    private boolean minimumPlayers() {
        int count = 0;
        
        if((player1.getStrategyIndex() > 0) || humanCheck.isSelected()) {
            count++;
        }
        for(PlayerPanel playerPanel : playerPanelList) {
            if(playerPanel.getStrategyIndex() > 0) {
            count++;
        }
        }
        
        return (count > 1);
    }

    /**
     * Returns a boolean value showing whether the dialog results are ready or not.
     * @return  A boolean, true if ready, false if not.
     */
    public boolean isReady()
    {
        return this.ready;
    }

    /**
     * Sets the ready boolean. Indicating the state of the dialog.
     * @param   readyState  The state of the dialog, meaning if it have all its info..
     */
    private void setReady(boolean readyState)
    {
        this.ready = readyState;
    }

    /**
     * Returns the GameManager created by the window, if it is not yet complete or something went wrong, it will return null.
     * @return  A GameManager instance or null.
     */
    public GameManager getGameManager()
    {
        return gameManager;
    }

    /**
     * A panel for selecting a strategy for a player
     * 
     * @author Christian Thoudahl
     * @author Martin Nicklas Jørgensen
     * @version 1.1
     */
    private class PlayerPanel extends JPanel
    {
        private JLabel    nameLabel;
        private String    name;
        private Color     color;
        private JComboBox strategies;

        /**
         * Constructor for objects of class PlayerPanel
         * 
         * @oaram  color   The color of the player
         * @param  name    The name of the player
         */
        public PlayerPanel(Color color, String name)
        {
            this.setLayout(new FlowLayout());
            this.name = name;

            // Retrieve the strategy names from the factory and put them in a combo box
            this.strategies = new JComboBox(StrategyFactory.getStrategyTypes().keySet().toArray());
            this.strategies.insertItemAt("", 0);
            this.strategies.setSelectedIndex(0);

            // Create a name label and set its color to this player's color
            this.nameLabel = new JLabel(name);
            this.add(nameLabel);
            this.add(new JLabel("Strategy: "));
            this.add(strategies);
            this.nameLabel.setForeground(color);

            this.color = color;
        }

        /**
         * Returns the idnex of the selected strategy.
         * @return  An int for the selected strategies index.
         */
        public int getStrategyIndex() {
            return strategies.getSelectedIndex();
        }

        /**
         * Gets the name of the panel
         * 
         * @return the name of the panel
         */
        public String getName()
        {
            return this.name;
        }

        /**
         * Gets the color of the player
         * 
         * @return the color
         */
        public Color getColor()
        {
            return this.color;
        }

        /**
         * Gets the player strategy that corresponds to the chosen strategy in the panel.
         * Returns null if no strategy has been selected.
         * 
         * @return the corresponding player strategy
         */
        public StrategyFactory.Type getStrategy()
        {
            // Get an instance of the strategy and return it
            String item = (String)this.strategies.getSelectedItem();
            return StrategyFactory.getStrategyTypes().get(item);
        }

        /**
         * Enables/disables the combo box
         * 
         * @param enabled   whether or not the combo box should be enabled
         */
        public void setComboBoxEnabled(boolean enabled)
        {
            this.strategies.setEnabled(enabled);
        }
    }
}
