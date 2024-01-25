/**
 * includes all variables used in environment class and also the other classes and all methods required to play the game
 * @author Nuri Basar, Student ID: 2021400129
 * @since date: 12.04.2023
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
public class Environment {
    Player player = new Player();
    Arrow arrow = new Arrow();
    Bar bar = new Bar();
    ArrayList<Ball> balls = new ArrayList<>(); // stores 3 ball from different level and their variable to use when we need
    ArrayList<Ball> playingBalls; // stores only specific balls which should be on the canvas, and we play the game with these balls .

    // calculating the radius of each level ball by using given formula
    public double level0Radius = minPossibleRadius * Math.pow(RADIUS_MULTIPLIER, 0);
    public double level1Radius = minPossibleRadius * Math.pow(RADIUS_MULTIPLIER, 1);
    public double level2Radius = minPossibleRadius * Math.pow(RADIUS_MULTIPLIER, 2);

    // adding the balls to balls list
    public void creatingBalls() {
        balls.clear();
        balls.add(new Ball((SCALE_X / 4), 0.5, MIN_POSSIBLE_HEIGHT * Math.pow(HEIGHT_MULTIPLIER, 0), level0Radius, SCALE_X / PERIOD_OF_BALL));
        balls.add(new Ball((SCALE_X / 4), 0.5, MIN_POSSIBLE_HEIGHT * Math.pow(HEIGHT_MULTIPLIER, 1), level1Radius, -SCALE_X / PERIOD_OF_BALL));
        balls.add(new Ball((SCALE_X / 4), 0.5, MIN_POSSIBLE_HEIGHT * Math.pow(HEIGHT_MULTIPLIER, 2), level2Radius, SCALE_X / PERIOD_OF_BALL));
        playingBalls = new ArrayList<>(balls); // copying the balls to playingBalls because we have to start the game with these 3 balls
    }

    // variables given in the description
    public final static int TOTAL_GAME_DURATION = 40000;
    public final static double SCALE_Y = 9.0;
    public final static double SCALE_X = 16.0;
    public final static int PAUSE_DURATION = 15;
    public final static int CANVAS_WIDTH = 800;
    public final static int CANVAS_HEIGHT = 500;
    public final static int PERIOD_OF_PLAYER = 6000;
    public final static double PLAYER_WIDTH_HEIGHT_RATE = 37.0 / 27.0;
    public final static double PLAYER_HEIGHT_SCALE_RATE = 1.0 / 8.0;
    public final static double PERIOD_OF_ARROW = 1500;
    public final static int PERIOD_OF_BALL = 15000;
    public final static double HEIGHT_MULTIPLIER = 1.75;
    public final static double RADIUS_MULTIPLIER = 2.0;
    public final static double PLAYER_HEIGHT = SCALE_Y * PLAYER_HEIGHT_SCALE_RATE;
    public final static double PLAYER_WIDTH = PLAYER_HEIGHT / PLAYER_WIDTH_HEIGHT_RATE;
    public final static double minPossibleRadius = SCALE_Y * 0.0175;
    public final static double MIN_POSSIBLE_HEIGHT = PLAYER_HEIGHT * 1.4;
    public final static double GRAVITY = 0.0000003 * SCALE_Y;
    public final static double TIME_BAR_HEIGHT = 0.5;
    public static double playerXAxis = SCALE_X / 2;
    public static final double PLAYER_Y_AXIS = PLAYER_HEIGHT / 2;
    public static double rectangleWidth = Environment.SCALE_X / 2; // this is used to draw the time bar, and it should change about time
    public static double rectangleColor = 255; // the same as rectangleWidth
    public static double arrowXAxis = 0.0; // the x coordinate of the arrow, it is determined by a person playing the game
    public static double arrowYAxis = 0.0; // the y coordinate of the arrow, it changes and grows with respect to time
    public static boolean isArrowGone = false; // to check the situation of the arrow whether it is released or not
    public static boolean haveGamerWon = false;
    public static boolean isGameFinished = false;
    public static boolean isGameOver = false;
    public static boolean shouldBallsDeleted = false;

    /**
     * puts the background to the canvas
     */
    public void displayingBackground() {
        StdDraw.picture(SCALE_X / 2, SCALE_Y / 2, "background.png", SCALE_X, SCALE_Y);
    }

    /**
     * draws the player on the canvas, and also player can be controlled by keyboard thanks to player class
     *
     * @param timeDifference is the difference between last time and previous time. It is used to change the x coordinate of the player
     */
    public void playerMovement(double timeDifference) {
        player.playerMovementInPLayerClass(timeDifference);
        StdDraw.picture(playerXAxis, PLAYER_Y_AXIS, "player_back.png", PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    /**
     * calculates the y velocity of the balls and assign it the balls respectively.
     */
    public void velocityFinding() {
        for (Ball ball : balls) {
            ball.velocityY = Math.pow(2 * ball.ballMaxHeight * GRAVITY, 0.5); // we need to use physics formula to calculate the velocity from the max height
        }
    }

    /**
     * checks whether the game is finished or not, and moves the ball by using ball class
     *
     * @param timeArray is an array list including all spending times form the starting time
     */
    public void ballMovement(ArrayList<Double> timeArray) {
        if (playingBalls.size() == 0) { // one of the conditions to finish the game is the empty playingBalls
            isGameFinished = true;
            haveGamerWon = true;
        } else {
            for (Ball ball : playingBalls) {
                ball.mainBallMovement(timeArray);
            }
        }
    }

    /**
     * controls the ball-arrow collision by checking the coordinates of the ball and arrow. if they collide, method splits them except for level 0 ball into to two small balls
     *
     * @param timeArray is an array list including all spending times form the starting time
     */
    public void ballArrowCollision(ArrayList<Double> timeArray) {
        ArrayList<Ball> canBoom = new ArrayList<>();
        // controls the coordinates of the balls. if they are in given interval, they will be added the array list containing the ball can explode
        for (Ball ball : playingBalls) {
            if (ball.ballX + ball.ballRadius >= arrowXAxis && arrowXAxis >= ball.ballX - ball.ballRadius) {
                if (ball.currentY <= arrowYAxis * 2)
                    canBoom.add(ball);
            }
        }
        // controls the which ball in canBoom array should explode
        if (canBoom.size() == 1) {
            Ball willBoom = canBoom.get(0);
            if (willBoom.ballRadius == level0Radius) {
                playingBalls.remove(willBoom); // the ball exploded should be deleted
                isArrowGone = false; // after the contact arrow should disappear and its information should be reset.
                arrowYAxis = 0.0;
            } else if (willBoom.ballRadius == level1Radius) { // if the ball is level 1, it should split into two level 0 balls
                playingBalls.remove(willBoom);
                playingBalls.add(new Ball(willBoom.ballX, willBoom.currentY, balls.get(0).ballMaxHeight, level0Radius, willBoom.velocityX)); // adding the new balls
                playingBalls.get(playingBalls.size() - 1).velocityY = balls.get(0).velocityY; // assigning their y velocity to the y velocity of the ball at te same level
                playingBalls.get(playingBalls.size() - 1).extraTime = timeArray.get(timeArray.size() - 1); // they should act like they are at the base point.
                playingBalls.add(new Ball(willBoom.ballX, willBoom.currentY, balls.get(0).ballMaxHeight, level0Radius, -willBoom.velocityX));
                playingBalls.get(playingBalls.size() - 1).velocityY = balls.get(0).velocityY;
                playingBalls.get(playingBalls.size() - 1).extraTime = timeArray.get(timeArray.size() - 1);
                isArrowGone = false;
                arrowYAxis = 0.0;
            } else { // if the ball is level 2, it should split into two level 1 balls
                playingBalls.remove(willBoom);
                playingBalls.add(new Ball(willBoom.ballX, willBoom.currentY, balls.get(1).ballMaxHeight, level1Radius, willBoom.velocityX));
                playingBalls.get(playingBalls.size() - 1).velocityY = balls.get(1).velocityY;
                playingBalls.get(playingBalls.size() - 1).extraTime = timeArray.get(timeArray.size() - 1);
                playingBalls.add(new Ball(willBoom.ballX, willBoom.currentY, balls.get(1).ballMaxHeight, level1Radius, -willBoom.velocityX));
                playingBalls.get(playingBalls.size() - 1).velocityY = balls.get(1).velocityY;
                playingBalls.get(playingBalls.size() - 1).extraTime = timeArray.get(timeArray.size() - 1);
                isArrowGone = false;
                arrowYAxis = 0.0;
            }
            // controls the which ball in canBoom array should explode, because one arrow can explode only one ball
        } else if (canBoom.size() > 1) {
            Ball willBoom = canBoom.get(0);
            for (Ball ball : canBoom) {
                if (ball.ballY < willBoom.currentY)
                    willBoom = ball;
            }
            if (willBoom.ballRadius == level0Radius) {
                playingBalls.remove(willBoom);
                isArrowGone = false;
                arrowYAxis = 0.0;
            } else if (willBoom.ballRadius == level1Radius) {
                playingBalls.remove(willBoom);
                playingBalls.add(new Ball(willBoom.ballX, willBoom.currentY, balls.get(0).ballMaxHeight, level0Radius, willBoom.velocityX));
                playingBalls.get(playingBalls.size() - 1).velocityY = balls.get(0).velocityY;
                playingBalls.get(playingBalls.size() - 1).extraTime = timeArray.get(timeArray.size() - 1);
                playingBalls.add(new Ball(willBoom.ballX, willBoom.currentY, balls.get(0).ballMaxHeight, level0Radius, -willBoom.velocityX));
                playingBalls.get(playingBalls.size() - 1).velocityY = balls.get(0).velocityY;
                playingBalls.get(playingBalls.size() - 1).extraTime = timeArray.get(timeArray.size() - 1);
                isArrowGone = false;
                arrowYAxis = 0.0;
            } else {
                playingBalls.remove(willBoom);
                playingBalls.add(new Ball(willBoom.ballX, willBoom.currentY, balls.get(1).ballMaxHeight, level1Radius, willBoom.velocityX));
                playingBalls.get(playingBalls.size() - 1).velocityY = balls.get(1).velocityY;
                playingBalls.get(playingBalls.size() - 1).extraTime = timeArray.get(timeArray.size() - 1);
                playingBalls.add(new Ball(willBoom.ballX, willBoom.currentY, balls.get(1).ballMaxHeight, level1Radius, -willBoom.velocityX));
                playingBalls.get(playingBalls.size() - 1).velocityY = balls.get(1).velocityY;
                playingBalls.get(playingBalls.size() - 1).extraTime = timeArray.get(timeArray.size() - 1);
                isArrowGone = false;
                arrowYAxis = 0.0;
            }
        }
    }

    /**
     * moves the arrow by using the arrow class
     *
     * @param timeDifference is the difference between last time and previous time. It is used to change the y coordinate of the arrow and grow it
     */
    public void arrowMovement(double timeDifference) {
        arrow.arrow(timeDifference);
    }

    /**
     * if the game is finish, we need to create a block to show what the gamer can do. The method does it, but it can change with respect to the result of the game.
     */
    public void replayingTheGame() {
        StdDraw.setPenColor(StdDraw.BLACK);
        if (haveGamerWon) { // Gamer wins
            StdDraw.picture(SCALE_X / 2, SCALE_Y / 2.3, "game_screen.png", SCALE_X / 3.8, SCALE_Y / 4);
            StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.0, "You Won!");
            StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.3, "To Replay Click “Y”");
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.6, "To Quit Click “N”");
        }
        if (isGameOver) { // Gamer loses
            StdDraw.picture(SCALE_X / 2, SCALE_Y / 2.3, "game_screen.png", SCALE_X / 3.8, SCALE_Y / 4);
            StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.0, "Game Over!");
            StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.3, "To Replay Click “Y”");
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.6, "To Quit Click “N”");
        }
    }

    /**
     * some variable can be changed, so if the gamer want to play the game again, we need to reset them.
     * the method resets them
     */
    public void resetAllVariables() {
        playerXAxis = SCALE_X / 2;
        rectangleWidth = Environment.SCALE_X / 2;
        rectangleColor = 255;
        arrowXAxis = 0.0;
        arrowYAxis = 0.0;
        isArrowGone = false;
        haveGamerWon = false;
        isGameFinished = false;
        isGameOver = false;
        shouldBallsDeleted = false;
    }

    /**
     * includes all games methods
     */
    public void mainGameMethod() {
        ArrayList<Double> takingTime = new ArrayList<>(); // stores the passing time
        double startingTime = System.currentTimeMillis();
        creatingBalls();
        velocityFinding();
        takingTime.add(startingTime);
        while (true) {
            while (true) {
                // If game is finish, break the loop
                if (Environment.isGameFinished) {
                    break;
                }
                double currentTime = System.currentTimeMillis(); // taking the passing time
                double timeDifference = currentTime - takingTime.get(takingTime.size() - 1); // the difference between the last taken time and previous time
                takingTime.add(currentTime);
                displayingBackground();
                bar.displayingBar(); // put the bar.png to the canvas
                bar.drawingBar(timeDifference); // draws the time bar
                player.ballPlayerCollision(playingBalls); // controls whether player and one of the balls can collide
                arrowMovement(timeDifference);
                playerMovement(timeDifference);
                ballArrowCollision(takingTime);
                if (!shouldBallsDeleted) // if one of balls touches the player, balls shouldn't appear on the canvas
                    ballMovement(takingTime);
                replayingTheGame();
                StdDraw.show();
                StdDraw.pause(Environment.PAUSE_DURATION);
                StdDraw.clear();
            }
            // after the game finish
            if (StdDraw.isKeyPressed(KeyEvent.VK_N)) // If gamer press the button of the N, the canvas should be closed
                System.exit(0);
            if (StdDraw.isKeyPressed(KeyEvent.VK_Y)) { //  If gamer press the button the Y,  the game should start again
                resetAllVariables();
                break;
            }
        }
        mainGameMethod(); // If game start again, we call the function again
    }
}
