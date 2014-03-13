package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.image.MemoryImageSource;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Game;
import model.GameManager;
import model.Player;
import model.Position;


/**
 * An image panel whose individual pixels can be painted.
 * 
 * @author Daniel Hessellund Egeberg
 * @author Søren Dahlgaard
 * @version 1
 */
public class TronDisplayPanel extends JPanel implements Observer
{
    private int                 scaleFactor;
    private int                 width;
    private int                 height;
    private MemoryImageSource   source;
    private int                 pixels[]; //img[x][y] == pixels[width * scaleFactor * scaleFactor * y + x * scaleFactor]
    private Image               image;
    
    /**
     * Creates a new image panel with a specified dimension and scaling factor.
     * 
     * @param   width       the width of the panel
     * @param   height      the height of the panel
     * @param   scaleFactor the scaling factor
     */
    public TronDisplayPanel(int width, int height, int scaleFactor)
    {
        this.width         = width;
        this.height        = height;
        this.scaleFactor   = scaleFactor;
        
        int actualWidth = this.width * this.scaleFactor;
        int actualHeight = this.height * this.scaleFactor;
        
        this.pixels = new int[actualWidth * actualHeight];
        
        this.source = new MemoryImageSource(actualWidth, actualHeight, this.pixels, 0, actualWidth);
                                       
        this.source.setAnimated(true);
        this.image = this.createImage(this.source);
    }
    
    /**
     * Paints a pixel at the specified position.
     * 
     * @param   x       the x coordinate
     * @param   y       the y coordinate
     * @param   color   the color to paint this position
     */
    public void setPixel(int x, int y, Color color)
    {
        int base = this.getUpperLeft(x, y);
        
        for (int i = 0; i < scaleFactor; ++i)
            for (int j = 0; j < scaleFactor; ++j)
                this.pixels[base + i + j * width * scaleFactor] = color.getRGB();
                
        this.source.newPixels(x * scaleFactor, y * scaleFactor, scaleFactor, scaleFactor);
    }
    
    /**
     * Called when an observed object has been changed.
     * 
     * @param   o   the observed object
     * @param   arg the argument given by the observed object
     */
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object arg)
    {
        if (o instanceof GameManager)
        {
            // If we write Collection<Object> or Collection<Player> BlueJ will give a stupid warning we don't want
            // This is an ugly way to avoid it, however.
            Collection players = (Collection)arg;
            for (Object obj : players)
            {
                Player player   = (Player)obj;
                
                Position previousPosition = player.getPreviousPosition();
                if (previousPosition != null)
                    this.paintBrightPixel(player.getColor(), previousPosition);
                
                this.paintPixel(player.getColor(), player.getPosition()); 
            }
        }
    }
    
    /**
     * Paints a pixel that is slightly brighter than the specified color at the specified position.
     * 
     * @param color the relative color to paint the pixel
     * @param position the position of the pixel to paint
     */
    private void paintBrightPixel(Color color, Position position)
    {
        Color brighterColor = TronColors.getBrighter(color);
        this.setPixel(position.getX(), position.getY(), brighterColor);
    }
    
    /**
     * Paints a pixel of the specified color at the specified position.
     * 
     * @param color the color to paint the pixel
     * @param position the position of the pixel to paint
     */
    private void paintPixel(Color color, Position position)
    {
        this.setPixel(position.getX(), position.getY(), color);
    }
    
    /**
     * Initializes the image display based on a board and a collection of players.
     * 
     * NOTE:
     * For some reason this method sometimes stalls the game at first load.
     * 
     * @param   game    the game
     * @param   players the players on the board
     */
    public void init(Game game, Collection<Player> players)
    {
        for (int i = 0; i < game.getWidth(); ++i)
            for (int j = 0; j < game.getHeight(); ++j)
                this.setPixel(i, j, !game.isOccupied(new Position(i, j)) ? Color.WHITE : Color.BLACK);
                
        for (Player player : players)
            this.setPixel(player.getPosition().getX(), player.getPosition().getY(), player.getColor());
    }
    
    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        graphics.drawImage(image, 0, 0, this);
    }
    
    /**
     * Gets the pixel index of upper left corner of a given position
     * 
     * @param   x   the x coordinate
     * @param   y   the y coordinate
     */
    private int getUpperLeft(int x, int y)
    {
        return width * scaleFactor * scaleFactor * y + x * scaleFactor;
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(height * scaleFactor, width * scaleFactor);
    }
}
