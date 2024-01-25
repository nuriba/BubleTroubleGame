/**
 * creates the canvas and its related variables and starts the game
 * @author Nuri Basar, Student ID: 2021400129
 * @since date: 12.04.2023
 */
public class Nuri_Basar {
    public static void main(String[] args) {
        Environment environment = new Environment();
        StdDraw.setCanvasSize(Environment.CANVAS_WIDTH,Environment.CANVAS_HEIGHT); // creating canvas
        StdDraw.setXscale(0.0,Environment.SCALE_X); // setting the x scale
        StdDraw.setYscale(-1.0,Environment.SCALE_Y); // setting the y scale
        StdDraw.enableDoubleBuffering();
        environment.mainGameMethod(); // it calls the main method to play the game
    }

}