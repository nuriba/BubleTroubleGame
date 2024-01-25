/**
 * The class throws the arrow, and make it bigger
 * @author Nuri Basar, Student ID: 2021400129
 * @since date: 12.04.2023
 */
public class Arrow {
    /**
     * @param timeDifference is the difference between the last time and the other time just before the last time
     */
    public void arrow(double timeDifference){
        if (Environment.isArrowGone) {
            double eachTimeGrowing = (Environment.SCALE_Y / Environment.PERIOD_OF_ARROW) * timeDifference; // the amount of growing in the arrow per time difference
            //compare the size of the arrow with the canvas scale, if it is bigger, it should not be drawing
            if (Environment.arrowYAxis*2 <= Environment.SCALE_Y) {
                StdDraw.picture(Environment.arrowXAxis, Environment.arrowYAxis, "arrow.png",(8.0/37)*Environment.PLAYER_WIDTH , Environment.arrowYAxis * 2); // Drawing the arrow on the canvas
                Environment.arrowYAxis += eachTimeGrowing;
            }else{
                // to reset information related to arrow
                Environment.arrowYAxis=0.0;
                Environment.isArrowGone= false;
            }
        }
    }
}
