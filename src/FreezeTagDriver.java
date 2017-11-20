import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.util.Random;

/**
 * Project 5
 * @author Joseph Brooksbank
 * @version 11/12/17
 * Driver class for "Freeze Tag" rectangle game, draws rectangles and handles user interaction
 */
public class FreezeTagDriver {
    /** The size of the canvas */
    private static final int CANVAS_SIZE = 300;
    /** The max width of a rectangle */
    private static final int MAX_WIDTH = 30;
    /** The max height of a rectangle */
    private static final int MAX_HEIGHT = 30;
    /** The max velocity of a rectangle */
    private static final int MAX_VELOCITY = 2;

    /**
     * The main method of this driver class
     *
     */
    public static void main(String[] args) {
        /* Constructing the canvas */
        StdDraw.setCanvasSize(CANVAS_SIZE, CANVAS_SIZE);
        StdDraw.setXscale(0, CANVAS_SIZE);
        StdDraw.setYscale(0, CANVAS_SIZE);
        StdDraw.enableDoubleBuffering();

        boolean loop = true;
        Random rand = new Random();
        MovingRectangle[] rectangleArray = new MovingRectangle[5];

        /* Initializing the values of each rectangle */
        for (int i  = 0; i < rectangleArray.length; i++){
            rectangleArray[i] = new MovingRectangle(rand.nextInt(CANVAS_SIZE - MAX_WIDTH*2)+ MAX_WIDTH,
                    rand.nextInt(CANVAS_SIZE - MAX_HEIGHT*2)+ MAX_HEIGHT,
                    rand.nextInt(MAX_WIDTH)+10,
                    rand.nextInt(MAX_HEIGHT)+10,
                    rand.nextInt(MAX_VELOCITY)+1,
                    rand.nextInt(MAX_VELOCITY)+1, CANVAS_SIZE);
        }
        /* The main drawing loop */
        while(loop){

            loop = false;
            StdDraw.clear();

            /* Moving and drawing each rectangle, and checking if the mouse clicked on them */
            for (MovingRectangle rectangle : rectangleArray){
                rectangle.move();
                rectangle.draw();
                /* If mouse is inside the rectangle, freeze it */
                if (StdDraw.mousePressed()){
                    if (rectangle.containsPoint(StdDraw.mouseX(), StdDraw.mouseY())){
                        rectangle.setColor(Color.RED);
                        rectangle.setFrozen(true);
                    }
                }

                /* Continue the loop unless all rectangles are frozen */
                if (!(rectangle.isFrozen()))
                    loop = true;
            }

            /* Looping through array for each item in array to see if they intersect */
            for (int i = 0; i < rectangleArray.length; i++){
                for (int j = 0; j < rectangleArray.length; j++){
                    /* If looking at two different rectangles, the second one is frozen, and they intersect, unfreeze */
                    if (!(i == j) && rectangleArray[j].isFrozen()
                            && rectangleArray[i].isIntersecting(rectangleArray[j])){
                        rectangleArray[j].setFrozen(false);
                        rectangleArray[j].setRandomColor();
                    }
                }
            }

            /* Drawing the canvas */
            StdDraw.show();
            StdDraw.pause(15);
        }

        /* Drawing the rectangles one last time so the last rectangle turns red */
        for (MovingRectangle rectangle : rectangleArray){
            rectangle.draw();
        }
        /* Displaying the "You won! text in black */
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(CANVAS_SIZE/2, CANVAS_SIZE - CANVAS_SIZE/6, "You WON!");
        StdDraw.show();





    }

}
