/**
 * draws the time bar and makes calculation related to time bar and also put the bar.png on the canvas
 * @author Nuri Basar, Student ID: 2021400129
 * @since date: 12.04.2023
 */
public class Bar {
    /**
     * puts the bar.png on the canvas
     */
    public void displayingBar(){
        StdDraw.picture(Environment.SCALE_X/2,-Environment.TIME_BAR_HEIGHT,"bar.png",(1045*Environment.PLAYER_WIDTH)/27.0,1);
    }

    /**
     * creates time bar and makes it smaller and smaller with changing its color
     * @param timeDifference is the difference taken from Environment class
     */
    public void drawingBar (double timeDifference){
        // after time change, the color and the size of the time bar should change
        // timeDifference is used to calculate the amount of change

        //  sets the  new rgb color number
        double changeInColor = (255.0/Environment.TOTAL_GAME_DURATION)*timeDifference;
        Environment.rectangleColor-=changeInColor;
        StdDraw.setPenColor(255, (int) Environment.rectangleColor,0);
        // sets the new time bar size
        double changeInRectangle = (Environment.SCALE_X/Environment.TOTAL_GAME_DURATION)*timeDifference;
        Environment.rectangleWidth -= changeInRectangle/2;
        if (Environment.rectangleWidth>0.0) {
            StdDraw.filledRectangle(Environment.rectangleWidth, -Environment.TIME_BAR_HEIGHT, Environment.rectangleWidth, 0.25);
        }else{
            // If the size of the rectangle smaller than 0.0, this means that time is up.
            Environment.isGameFinished=true;
            Environment.isGameOver=true;
            Environment.shouldBallsDeleted=true;
        }
    }
}
