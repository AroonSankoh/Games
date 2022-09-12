
import objectdraw.*;
import java.awt.*;

/*
 * Definition of class of objects used to represent bar magnets.
 * DESCRIBE YOUR CLASS HERE
 *
 * YOUR NAME / LAB SECTION
 * DATE
 *
 * Complete the implementation of these methods and add
 * the others described in the lab handout.
 */
public class Magnet {

    //  dimensions of magnets
    private static final double MAGNET_WIDTH = 150;
    private static final double MAGNET_HEIGHT = 50;

    //  Box representing perimeter of magnet
    private FramedRect box;

    //  Create a new magnet at location upperLeft
    public Magnet(Location upperLeft, DrawingCanvas canvas) {
    }

    // returns the upper-left coordinates of the magnet
    public Location getLocation() {
        return box.getLocation();
    }

    public void move(double xoff, double yoff) {

    }

    public void moveTo(Location point) {

    }

    /*
     * This should return true if the given point is within the magnet.
     * The current implementation does not do this!!!  You must change
     * the body of this method so that it has the proper functionality!!!
     */
    public boolean contains(Location point) {
        return false; // REPLACE THIS LINE OF CODE WITH THE CORRECT IMPLEMENTATION
    }

    // returns the width of the magnet
    public double getWidth() {
        return MAGNET_WIDTH;
    }

    // returns the height of the magnet
    public double getHeight() {
        return MAGNET_HEIGHT;
    }

}
