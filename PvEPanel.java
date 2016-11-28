package pong;

@SuppressWarnings("serial")
public class PvEPanel extends PvPPanel {
	/*PvEPanel:
	 * Author: Anuraag Bharadwaj
	 * Year: 2016
	 * Super Class: PvPPanel
	 * Purpose: Extends the PvPPanel by overriding paddle control
	 * such that the user controls one panel and the other panel is 
	 * controlled by the CPU for the Player vs Comp mode.
	 * Functionality similar to PvPPanel (see @pong.PvPPanel)
	 * 
	 * Includes:
	 * void updatePaddles(): Overrides PvPPanel.updatePaddles
	 * Adds CPU component, having one panel move independent of any
	 * keys pressed. 
	 */
	
	@Override
	public void updatePaddles(){
		/*updatePaddles: No Parameters
		 * Overrides PvPPanel.updatePaddles()
		 * Moves the Player's according the pressed keys if the move would 
		 * keep the player withins the bounds of the screen.
		 * The Computer's paddle moves in accordance where it predicts will
		 * be. In order to this, the paddle's top and bottom bound are
		 * compared to the ball's position and its direction in 10 updates
		 * from the current time. 
		 */
		
		//keyPressed[0] represents the UP key and will move the paddle up
		//This only occurs if the panel will remain on screen.
		if (keysPressed[0]){
			if (paddleCenters[0]>PvPPanel.paddleLength/2){
				paddleCenters[0] -= paddleSpeed;
			}
		}

		//keyPressed[1] represents the DOWN key and will move the paddle up
		//This only occurs if the panel will remain on screen.
		if (keysPressed[1]){
			if (paddleCenters[0]<480-PvPPanel.paddleLength/2){
				paddleCenters[0] += paddleSpeed;
			}
		}
		
		//Uses the current ball direction, speed and position to predict
		//the theoretical position of the code after 10 frame updates
		//If this position is higher than the paddles constraints, the paddle
		//is moved up.
		if (ball[1]+10*ballSpeed*Math.sin(ballDirection)<paddleCenters[1]-paddleLength/2){
			if (paddleCenters[1]>PvPPanel.paddleLength/2){
				paddleCenters[1] -= paddleSpeed;
			}
		}
		
		//Uses the current ball direction, speed and position to predict
		//the theoretical position of the code after 10 frame updates
		//If this position is lower than the paddles constraints, the paddle
		//is moved down.
		if (ball[1]+10*ballSpeed*Math.sin(ballDirection)>paddleCenters[1]+paddleLength/2){
			if (paddleCenters[1]<480-PvPPanel.paddleLength/2){
				paddleCenters[1] += paddleSpeed;
			}
		}
	}

}
