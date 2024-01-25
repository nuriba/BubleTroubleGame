/**
 * moves the player, sets the x coordinate of arrow and checks collision of the balls and player
 * @author Nuri Basar, Student ID: 2021400129
 * @since date: 12.04.2023
 */
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {
    /**
     * moves player and sets the x coordinate of arrow
     * @param timeDifference is the difference taken from Environment class
     */
    public  void playerMovementInPLayerClass(double timeDifference){
        double currentPlayerX = Environment.playerXAxis; // the current x coordinate of player
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)){ // if gamer press the right button on the keyboard, player should move to right
            if ( currentPlayerX+(Environment.PLAYER_WIDTH/2)<= Environment.SCALE_X) { // controls the coordinate of player if it is on the canvas or not
                currentPlayerX += (Environment.SCALE_X/Environment.PERIOD_OF_PLAYER)*timeDifference; // determines the new x coordinate
            }
            Environment.playerXAxis= currentPlayerX; // sets it as the new coordinate
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)){ // if gamer press the left button on the keyboard, player should move to left
            if(currentPlayerX-(Environment.PLAYER_WIDTH/2)>=0.0){ // controls the coordinate of player if it is on the canvas or not
                currentPlayerX -= (Environment.SCALE_X/Environment.PERIOD_OF_PLAYER)*timeDifference; // determines the new x coordinate
            }
            Environment.playerXAxis= currentPlayerX;// sets it as the new coordinate
        }
        if (! Environment.isArrowGone) { // Arrow shouldn't be released to create a new arrow on the canvas
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) { // If gamer press the space on the keyboard, we need to release the arrow
                Environment.arrowXAxis = Environment.playerXAxis; // the x coordinate of the arrow is the same as the x coordinate of the player
                Environment.arrowYAxis = 0.0;
                Environment.isArrowGone = true;
            }
        }
    }

    /**
     * checks collision of the balls and player. Important Note: the player is considered as a rectangle
     * @param playingBalls is the list of the balls which are shown on the canvas
     */
    public void ballPlayerCollision(ArrayList<Ball> playingBalls){
        for (Ball ball: playingBalls){
            if (ball.ballX<Environment.playerXAxis){  // the ball is left side of the player
                if(ball.ballX+ball.ballRadius> Environment.playerXAxis-Environment.PLAYER_WIDTH/2){
                    if(ball.currentY-ball.ballRadius<Environment.PLAYER_Y_AXIS+Environment.PLAYER_HEIGHT/2) {
                        // If the ball touches the player, game is finish, and gamer is lost
                        Environment.isGameFinished = true;
                        Environment.isGameOver = true;
                        Environment.shouldBallsDeleted=true;
                    }
                }
            }else if(ball.ballX>Environment.playerXAxis){ // the ball is right side of the player
                if(ball.ballX-ball.ballRadius<Environment.playerXAxis+Environment.PLAYER_WIDTH/2){
                    if(ball.currentY-ball.ballRadius<Environment.PLAYER_Y_AXIS+Environment.PLAYER_HEIGHT/2) {
                        // If the ball touches the player, game is finish, and gamer is lost
                        Environment.isGameFinished = true;
                        Environment.isGameOver = true;
                        Environment.shouldBallsDeleted=true;
                    }
                }
            }
        }
    }
}
