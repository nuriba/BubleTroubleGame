/**
 * create a ball ,and it makes projectile motion and elastic collision
 * @author Nuri Basar, Student ID: 2021400129
 * @since date: 12.04.2023
 */

import java.util.ArrayList;
public class Ball {
    public double ballX; // the coordinate of the ball in the X-axis
    public double ballY; // the coordinate of the ball in the Y-axis, but it isn't  updated
    public  double ballMaxHeight; //  maximum height the ball can reach
    public double ballRadius; // the radius of the ball
    public double velocityX; // the velocity of the ball along the x-axis
    public double velocityY; // the velocity of the ball along the y-axis
    public double currentY = ballY; // it is similar to variable ballY, but this is updated continuously
    public double extraTime = 0; // to store the time used to calculate time difference for velocity y
    public Ball(double initialBallX, double initialBallY, double ballMaxHeight, double ballRadius, double velocityX){
        this.ballX=initialBallX;
        this.ballY = initialBallY;
        this.ballMaxHeight = ballMaxHeight;
        this.ballRadius=ballRadius;
        this.velocityX=velocityX;
    }

    /**
     * checks all values to make elastic collision and projectile motion, and draws the ball on the canvas
     * @param timeArray is an array list including all spending times form the starting time
     */
    public void mainBallMovement (ArrayList<Double> timeArray){
        double timeDifference = timeArray.get(timeArray.size()-1) - timeArray.get(timeArray.size()-2); // the difference between last time and previous time
        double timeDifferenceY; // this is calculated with the similar way with timeDifference, but resultant can change with respect to the coordinate of the ball
        // calculating timeDifferenceY with different way
        if (extraTime==0){
            timeDifferenceY = timeArray.get(timeArray.size()-1) - timeArray.get(0);
        }else{
            timeDifferenceY = timeArray.get(timeArray.size()-1) - extraTime;
        }
        currentY = ballY + (velocityY*timeDifferenceY - 0.5*Environment.GRAVITY*timeDifferenceY*timeDifferenceY); // calculating current Y location by using projectile motion formula
        if (currentY<ballRadius){ // the Y location of the ball cannot be smaller than ball radius. If so, it has to bounce and make elastic collision by changing some related variables
            currentY=ballRadius;
            ballY=ballRadius;
            extraTime= timeArray.get(timeArray.size()-1);
        }
        double deltaX = changeInX(timeDifference); // It is the amount of the change in the x-axis
        // the x coordinate of the ball should be between 0+ball radius and the max value of the x scale - ball radius. If not,
        // it has to make elastic collision.
        if (ballX + deltaX < ballRadius) {
            deltaX += (ballX - ballRadius);
            ballX = ballRadius - deltaX;
            velocityX *= -1;
        } else if (ballX + deltaX > 16.0 - ballRadius) {
            deltaX -= (16.0 - ballX - ballRadius);
            ballX = 16.0 - deltaX - ballRadius;
            velocityX *= -1;
        } else {
            ballX += deltaX;
        }
        StdDraw.picture(ballX,currentY,"ball.png",2*ballRadius,2*ballRadius); // drawing the ball on the canvas
    }

    /**
     * calculates how many units changed in the x-axis due to velocityX
     * @param timeDifference is the difference between last time and previous time.
     * @return the change in the x-axis
     */
    public double changeInX(double timeDifference){
        return velocityX*timeDifference;
    }
}
