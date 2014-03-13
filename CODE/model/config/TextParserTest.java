package model.config;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.config.TextParser;
import model.config.LineSegment;
import model.config.Configuration;

import model.Position;

/**
 * The test class TextParserTest.
 *
 * @author  Martin Nicklas Jørgensen
 * @version 1 (19-01-2011)
 */
public class TextParserTest extends junit.framework.TestCase
{
    private TextParser parser;
    /**
     * Default constructor for test class TextParserTest
     */
    public TextParserTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
        parser = null;
    }

    /**
     * Tests that the parser reports something fi the file is not found.
     */
    public void testFileNotFound() throws FileNotFoundException
    {
        try {
            parser = new TextParser("myNonExcisting.File");
            fail("The parser should have reported the file as non excisting!");
        }
        catch (FileNotFoundException ex) {
            // do nothing, we got what we wanted.
        }
    }

    /**
     * Tests that the parser gives the correct error upon finding a broken file
     * Broken.tron is designed to give the specific message, other errors might occur on other files.)
     */
    public void testBrokenfile()
    {
        try {
            parser = new TextParser("broken.tron");
            parser.getConfiguration();

        }
        catch (ParserException ex) {
            assertEquals("File had invalid top line", ex.getMessage());
        }
        catch (FileNotFoundException ex) {
            // this should happen if broken file is not there (but in that case tha parser also behaves correctly, so it passes.)
        }
    }

    /**
     * Tests the parser ability to read the simple.tron file correctly.
     */
    public void testSimpleRead()
    {
        try {
            parser = new TextParser("simple.tron");
            Configuration gotFromParser = parser.getConfiguration();
            ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
            ArrayList<Position> positions = new ArrayList<Position>();

            positions.add(new Position(2, 2));
            positions.add(new Position(9, 9));

            Configuration whatWeWant = new Configuration(15, 15, 10, lines, positions);
        }
        catch (ParserException ex) {
            fail("The parser should be able to read the file correctly...:" + ex.getMessage());
        }
        catch (FileNotFoundException ex) {
            fail("Parser should be able to find this file...");
        }
    }
}
