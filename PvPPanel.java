package pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PvPPanel extends JPanel {
	/*PvPPanel:
	 * Author: Anuraag Bharadwaj
	 * Year: 2016
	 * Super Class: JPanel
	 * Purpose: Provides player platform for playing Player
	 * vs Player mode of Pong. Draws screen with paddles on either
	 * end and ball as well as keeping track of where the ball
	 * is and its current trajectory. 
	 * If the ball contacts a border, then it is reflected back
	 * into play. If the ball hits a paddle, it is reflected 
	 * based on angle of incidence and location on the paddle
	 * that is struck. 
	 * Includes:
	 * void paintComponent(Graphics g): Overrides JComponent.paintComponent(Graphics g)
	 * Draws in the borders and calls drawBall,drawPaddles and printPlayerScores
	 * 
	 * void printPlayerScores(Graphics g): Displays the corresponding player's
	 * score on their side of the board
	 * 
	 * void drawBall(Graphics g): Draws the ball in based on its current location
	 * and the static ball radius
	 * 
	 * void drawPaddles(Graphics g): Draws in the paddles in their rows, based on
	 * their centers and static paddle length
	 * 
	 * void resetBoard(): Starts the next point by resetting the ball
	 * 
	 * void updatePaddles(): Moves the center of panels based off on the keys
	 * pressed by the user(s)
	 * 
	 * void increaseBallSpeed(): Increases the speed of the ball as the game
	 * progresses in order to increase dificulty of play
	 * 
	 * boolean updateBall(): Moves the ball's center based on the ball's current
	 * position, its trajectory and its surroundings (i.e. borders, paddles)
	 */
	
	private static final int ballRadius = 10; //Pixel radius of ball
	protected static final int paddleLength = 80; //Pixel length of paddles
	private static final double maxBallSpeed = 4.75; //Sets a cap of the ball speed
	private static final int[] paddleColumns = {15,770}; //Columns in which paddles are
	//paddleCenterTolerance is the percentage of the paddle that is the center.
	private static final double paddleCenterTolerance = 0.15; 
	//Distance traveled by paddles per update
	protected static final double paddleSpeed = 1.89; 
	
	
	protected double[] paddleCenters = {240,240}; //Rows of paddle's centers
	protected double[] ball = {400,240}; //Center of ball
	protected double ballSpeed = 2.70; //Distance travelled by ball per update 
	//ballDirection tracks ball's trajectory relative to the horizontal
	//ballDirection = 0 - the ball is moving horizontally right
	protected double ballDirection = Math.PI;
	//keysPressed tracks whether or not a certain key is pressed.
	//Tracks UP,DOWN,W,S in that order. Allows for multiple key presses
	protected boolean[] keysPressed = {false,false,false,false};
	protected int[] playerScores = {0,0}; //Tracks Player Scores
	
	@Override
	protected void paintComponent(Graphics g){
		/*paintComponent(Graphics g)
		 * Called whenever JComponent.repaint() is called. 
		 * Draws in the borders and the line in the middle of the 
		 * screen to signify to the players each side of the field.
		 * Calls drawPaddles(g),drawBall(g) and printPlayerScores(g)
		 */
		
		//Calls JComponent.paintComponent
		super.paintComponent(g);
		//Draws in central, upper and lower boundaries with thickness 3
		g.setColor(Color.black);
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(480,801));
		for(int i = 0;i<3;i++){
			g.drawLine(0, i, 801, i);
			g.drawLine(0, 480-i, 801, 480-i);
			g.drawLine(399+i, 0, 399+i, 480);
		}
		//Calls other draw functions using the Graphics given
		drawPaddles(g);
		drawBall(g);
		printPlayerScores(g);
	}
	
	private void printPlayerScores(Graphics g) {
		/*printPlayerScores(Graphics g)
		 * Called whenever PvPPanel.paintComponent is called
		 * Draws the Players' scores on their respect sides of the field
		 */
		
		//Sets font to specific to size and style
		Font font2 = new Font(g.getFont().getFontName(),Font.BOLD,48);
		g.setFont(font2);
		//Prints the scores in the respective spots
		g.drawString(" " +playerScores[0], 350, 50);
		g.drawString(" " +playerScores[1], 398, 50);
	}

	private void drawBall(Graphics g){
		/*drawBall(Graphics g)
		 * Draws ball based on ball center and static radius
		 */
		g.fillOval((int)ball[0]-ballRadius,(int)ball[1]-ballRadius,
					ballRadius*2, ballRadius*2);
	}
	
	private void drawPaddles(Graphics g){
		/*drawPaddles(Graphics g)
		 * Draws the paddle in specific columns. Rows are based on 
		 * static paddle length and paddle centers
		 */
		
		//Draws each side 
		for(int i = 0;i<2;i++){
			//Draws in paddles with thickness of 5 
			for(int j = 0;j<5;j++){
				g.drawLine(15+j+(771-2*j)*i, (int)(paddleCenters[i]-paddleLength/2), 
					15+j+(771-2*j)*i, (int)(paddleCenters[i]+paddleLength/2));
			}
		}
	}
	
	public void resetBoard(){
		/*resetBoard()
		 * Resets the ball with original location, trajectory and speed
		 */
		double[] array = {400,240};
		ball = array;
		ballDirection = Math.PI;
		ballSpeed = 2.60;
	}
	
	public void updatePaddles(){
		/*updatePaddles()
		 * Moves paddle location based on the current keys that are 
		 * being pressed at the time. W and S move the left paddle.
		 * UP and DOWN move the right paddle.
		 */
		
		// Moves the right paddle up if UP is pressed and the paddle won't
		// go over the edge of the screen
		if (keysPressed[0]){
			if (paddleCenters[1]>PvPPanel.paddleLength/2){
				paddleCenters[1] -= paddleSpeed;
			}
		}
		
		// Moves the right paddle down if DOWN is pressed and the paddle won't
		// go over the edge of the screen
		if (keysPressed[1]){
			if (paddleCenters[1]<480-PvPPanel.paddleLength/2){
				paddleCenters[1] += paddleSpeed;
			}
		}
		
		// Moves the left paddle up if W is pressed and the paddle won't
		// go over the edge of the screen
		if (keysPressed[2]){
			if (paddleCenters[0]>PvPPanel.paddleLength/2){
				paddleCenters[0] -= paddleSpeed;
			}
		}
		
		// Moves the left paddle down if S is pressed and the paddle won't
		// go over the edge of the screen
		if (keysPressed[3]){
			if (paddleCenters[0]<480-PvPPanel.paddleLength/2){
				paddleCenters[0] += paddleSpeed;
			}
		}
	}

	public void increaseBallSpeed(){
		/*increaseBallSpeed()
		 * Increases ball speed on every update relative to the
		 * current speed and the static max speed, slowly approaching
		 * it but never completely reaching it.
		 */
		ballSpeed += (maxBallSpeed-ballSpeed)/3000;
	}

	public boolean updateBall(){
		/*updateBall()
		 * Moves the ball's center based off current location and
		 * the ball's trajectory. The ball's trajectory is changed
		 * if the ball will run into a paddle or a border.
		 * Additionally the method returns a boolean that determines
		 * whether the ball has crossed either end aka whether
		 * the point is over. 
		 */
		
		//Tests the ball's next move will collide with the left paddle
		if(ball[0]+ballSpeed*Math.cos(ballDirection)-ballRadius<=paddleColumns[0]&
				ball[0]+ballSpeed*Math.cos(ballDirection)-ballRadius>=paddleColumns[0]-ballSpeed){
			double[] paddleBounds = {paddleCenters[0]-paddleLength/2,paddleCenters[0]+paddleLength/2};
			if (ball[1]>=paddleBounds[0]&&ball[1]<=paddleBounds[1]){
				double ballDeflection = (ball[1]-((paddleBounds[0]+paddleBounds[1])/2))/paddleLength;
				//If the ball hits the paddle in its middle based on 
				//paddle center tolerance, then it is reflected
				if (Math.abs(ballDeflection) < paddleCenterTolerance){
					ballDirection *= -1;
					ballDirection += Math.PI;
				}else{
					//If the ball doesn't hit the middle, the ball is
					//reflected at angle between 30 and 60 degrees and
					//in the direction that the ball came in at
					//The angle is linearly dependent on the percentage
					//of the paddle the ball hits away from the center of the
					//paddle.
					double m = (Math.PI/3-Math.PI/6)/(0.5-paddleCenterTolerance);
					double b = Math.PI/3-m/2;
					ballDirection = m*Math.abs(ballDeflection)+b;
					if (ballDeflection<0){
						//If the ball hits the bottom half, it is sent downwards
						ballDirection *= -1;
					}
				}
			}
		}
		//Tests the ball's next move will collide with the right paddle
		if (ball[0]+ballSpeed*Math.cos(ballDirection)-ballRadius<=paddleColumns[1]&
				ball[0]+ballSpeed*Math.cos(ballDirection)-ballRadius>=paddleColumns[1]-ballSpeed) {
			double[] paddleBounds = {paddleCenters[1]-paddleLength/2,paddleCenters[1]+paddleLength/2};	
			if (ball[1]>=paddleBounds[0]&&ball[1]<=paddleBounds[1]){
				double ballDeflection =(ball[1]-((paddleBounds[0]+paddleBounds[1])/2))/paddleLength;
				//If the ball hits the paddle in its middle based on 
				//paddle center tolerance, then it is reflected
				if (Math.abs(ballDeflection) < paddleCenterTolerance){
					ballDirection *= -1;
					ballDirection += Math.PI;
				}else{
					//If the ball doesn't hit the middle, the ball is
					//reflected at angle between 30 and 60 degrees and
					//in the direction that the ball came in at
					//The angle is linearly dependent on the percentage
					//of the paddle the ball hits away from the center of the
					//paddle.
					double m = (2*Math.PI/3-5*Math.PI/6)/(0.5-paddleCenterTolerance);
					double b = 2*Math.PI/3-m/2;
					ballDirection = m*Math.abs(ballDeflection)+b;
					if (ballDeflection<0){
						//If the ball hits the bottom half, it is sent downwards
						ballDirection *= -1;
					}
				}
			}
		}	
		//If the ball would strike the borders in its next move, it is reflected away
		if (ball[1]+ballSpeed*Math.sin(ballDirection)-ballRadius<=3||
				ball[1]+ballSpeed*Math.sin(ballDirection)+ballRadius>=477){
			ballDirection *= -1;
		}
		//The ball is updated to the next spot it will be at
		ball[0] = ball[0]+ballSpeed*Math.cos(ballDirection);
		ball[1] = ball[1]+ballSpeed*Math.sin(ballDirection);
		//Test the ball to see if it goes off screen and returns whether the game is over
		if ((ball[0]<0)|(ball[0]>801)){
			//Assigns the points the right player depending on where the ball goes off
			if (ball[0]<0){
				playerScores[1] += 1;
			}else{
				playerScores[0] += 1;
			}
			if (playerScores[0]==10|playerScores[1]==10){
				return true;	
			}
			//Resets the board if the ball goes off
			resetBoard();
		}
		return false;
 	}
	
}
