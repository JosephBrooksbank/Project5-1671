import java.util.Random;
import java.awt.Color;
import edu.princeton.cs.introcs.StdDraw;

/**
 * Project 5
 * @author Joseph Brooksbank
 * @version 11/12/17
 * Rectangle Class, holds all information about a Rectangle object and its methods to interact with other rectangles
 */
class MovingRectangle {

    /** X coordinate of the rectangle */
    private int xCoord;
    /** Y coordinate of the rectangle */
    private int yCoord;
    /** Half of the width of the rectangle */
    private int width;
    /** Half of the height of the rectangle */
    private int height;
    /** X velocity of the rectangle */
    private int xVelocity;
    /** Y velocity of the rectangle */
    private int yVelocity;
    /** Canvas size, as defined in the driver class */
    private int canvasSize;
    /** Color of the rectangle */
    private Color color;
    /** True if rectangle is frozen in place */
    private boolean frozen;
    /** Random obj for color changes */
    private Random rand = new Random();

    /**
     * Solo constructor of Rectangle class
     * @param xCoord:   The starting X coord
     * @param yCoord:   The starting Y coord
     * @param width:    The width
     * @param height:   The height
     * @param yVelocity: The Y velocity
     * @param xVelocity: The X velocity
     * @param canvasSize: The canvas size, as defined in the driver class
     */
    MovingRectangle(int xCoord, int yCoord, int width, int height, int yVelocity, int xVelocity, int canvasSize) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width = width/2;
        this.height = height/2;
        this.yVelocity = yVelocity;
        this.xVelocity = xVelocity;
        this.canvasSize = canvasSize;
        /* **NOTE ** Technically this could be considered bad practice to call a method that leaks "this"
        in a constructor, but I'm not entirely sure on what is actually bad practice in this situation and what isn't.
        I think the use of this particular method is not against java's standards.
         */
        setRandomColor();
        this.frozen = false;
    }

    /**
     * Method which draws the rectangle at its current position
     */
    void draw(){
        StdDraw.setPenColor(color);
        StdDraw.filledRectangle(xCoord, yCoord, width, height);
    }

    /**
     * Method which moves the rectangle based on its current velocities
     */
    void move(){
        if (!frozen){
            /* If hitting the edge, bounce off and change color  */
            if (xCoord + width >= canvasSize || xCoord - width <= 0){
                xVelocity *= -1;
                setRandomColor();
            }
            if (yCoord + height >= canvasSize || yCoord - height <= 0){
                yVelocity *= -1;
                setRandomColor();
            }
            /* Update position based on velocity */
            xCoord += xVelocity;
            yCoord += yVelocity;

        }
    }

    /**
     * Sets the rectangle to a specific color
     * @param c:    The color to change to
     */
    void setColor (Color c){
        this.color = c;
    }

    /**
     * Sets the rectangle to a random color
     */
    void setRandomColor(){
        int r = rand.nextInt(244) + 1;
        int g = rand.nextInt(244) + 1;
        int b = rand.nextInt(244) + 1;
        this.color = new Color(r,g,b);
    }

    /**
     * Method to check if a point is inside a rectangle
     * @param x:    The X coordinate of the point
     * @param y:    The Y coordinate of the point
     * @return :    True if point is inside rectangle
     */
    boolean containsPoint(double x, double y){
        boolean doesContain = true;

        if (!(x < xCoord + width && x > xCoord - width)){
            doesContain = false;
        }
        if (!(y < yCoord + height && y > yCoord - height )){
            doesContain = false;
        }
        return doesContain;
    }

    /**
     * Getter for frozen boolean
     * @return :    True if frozen
     */
    boolean isFrozen(){
        return frozen;
    }

    /**
     * Method to set frozen status
     * @param val : The state frozen will become
     */
    void setFrozen(boolean val){
        frozen = val;
    }

    /**
     * An int array for the top left corner of the rectangle
     * **NOTE** Could technically be a Point object, but I thought I should stick with what we "know'
     * @return : An int array coordinate pair
     */
    private int[] topLeft(){
        int topLeftX = xCoord - width;
        int topLeftY = yCoord + height;
        return new int[] {topLeftX, topLeftY};
    }

    /**
     * An int array for the bottom right corner of the rectangle
     * @return : An int array coordinate pair
     */
    private int[] bottomRight(){
        int bottomRightX = xCoord + width;
        int bottomRightY = yCoord - height;
        return new int[] { bottomRightX, bottomRightY};
    }

    /**
     * Method to determine if two rectangles are intersecting using two corner method
     * @param r:    The rectangle to be compared against
     * @return :    True if rectangles intersect
     */
    boolean isIntersecting(MovingRectangle r){
        if (topLeft()[0] > r.bottomRight()[0]
                || r.topLeft()[0] > bottomRight()[0])
            return false;

        if (topLeft()[1] < r.bottomRight()[1]
                || r.topLeft()[1] < bottomRight()[1])
            return false;

        return true;

    }

}
