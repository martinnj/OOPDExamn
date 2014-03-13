package view;

import java.awt.Color;

/**
 * Utility class for the game colors.
 * 
 * @author Daniel Hessellund Egeberg
 * @version 1
 */
public class TronColors
{
    /**
     * Gets a finite amount of colors.
     * 
     * @param   n   the number of colors to get
     * @return  an array consisting of n colors
     */
    public static Color[] getColors(int n)
    {
        Color[] colors = new Color[n];
        float step = 1.0f / n;
        for (int i = 0; i < n; ++i) {
            colors[i] = Color.getHSBColor(i * step, 0.7f, 0.5f);
        }
        
        return colors;
    }
    
    /**
     * Creates a brighter version of the specified color if possible.
     * 
     * @param   color   The color to brighten
     * @return  A brighter version of the input color
     */
    public static Color getBrighter(Color color)
    {
        // Make the color brighter by first converting it to HSB
        // and then increase the colors brightness to a max of 1.0f.
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        
        float newBrightness = hsb[2] + 0.5f;
        if (newBrightness > 1.0f)
            newBrightness = 1.0f;
        
        return Color.getHSBColor(hsb[0], hsb[1], newBrightness);
    }
}
