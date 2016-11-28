package pong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener, KeyListener{
	/*Pong: 
	 * Author: Anuraag Bharadwaj
	 * Year: 2016
	 * Interfaces: ActionListener, KeyListener
	 * Purpose: Creates a version of the Classical Videogame
	 * Pong (1972). The game is one or two player game in which
	 * there are two paddles on either end of a screen with a ball
	 * moving between them. The goal of the game is to get the ball
	 * past the opponent's paddle by bouncing it against your paddle.
	 * The game ends when you get the ball past the opponent's goal
	 * 10 times. 
	 * In this version, the user has choice between playing against 
	 * another person on the same screen or play against an inbuilt
	 * AI. After the game ends, the player is prompted with the option
	 * to play again or quit. 
	 * 
	 * Includes:
	 * Pong(): Creates a new frame for the entire game to load into.
	 * Intializes its size and adds a BaseNavigationPanel to it.
	 * 
	 * void main(String args[]): Starts the game by creating an 
	 * instance of the class (Pong).
	 * 
	 * void keyPressed(KeyEvent key): Controls for user to move cursors
	 * and paddles. When in PvP or PvE environment, tracks the keys currently
	 * pressed (see keyReleased).
	 * 
	 * void keyReleased(KeyEvent key): Sets the keys set by keyPressed to false
	 * when controlling paddles in the PvP or PvE environment. 
	 * 
	 * void keyTyped(KeyEvent key): Used to pause the game.
	 * 
	 * void actionPerformed(ActionEvent arg0): Acts the frame update. Called every
	 * 5 milliseconds by the Timer. 
	 */
	
	private JFrame jframe; //Window for the gameplay
	/* The following panels represent all of the different
	 * screens in which the game is played on. 
	 * Note: In order avoid confusion, they have been left as
	 * separate panels rather than consolidation into one JPanel. 
	 */
	private BaseNavigationPanel base;
	private EndScreenPanel endScreen;
	private PvPPanel pvp;
	private PvEPanel pve; 
	
	private String currentPanel; // Tracks the current panel add to jframe
	private Timer timer = new Timer(5,this); //Calls actionPerformed every 5 seconds
	private boolean paused = false; //Used to pause game
	
	public Pong(){
		//Intializes the game screen with the specifications below
		jframe = new JFrame ("Pong");
		jframe.setVisible(true);
		jframe.setSize(801,480);
		jframe.getContentPane().setSize(801, 480);
		jframe.setLocation(200, 75);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.addKeyListener(this);
		
		//Begins the game with the BaseNavigationPanel
		jframe.add(base = new BaseNavigationPanel());
		jframe.pack();
		currentPanel = "base"; 
	}
	
	public static void main(String[] args){
		/*main(String[] args):
		 * Called to start game. Creates an instance of the game.
		 */
		@SuppressWarnings("unused")
		Pong pong = new Pong();
	}

	@Override
	public void keyPressed(KeyEvent key) {
		/*keyPressed(KeyEvent key):
		 * Called whenever a key is pressed. Dependant on the current panel:
		 * 
		 * Base Navigation - Moves the cursor up and down if the UP key or 
		 * DOWN key is pressed. If the user pressed ENTER then the panel is
		 * removed and replaced by the corresponding panel.
		 * 
		 * End Screen - Moves the cursor up and down if the UP key or 
		 * DOWN key is pressed. If the user pressed ENTER then the panel is
		 * removed and replaced by the base navigation panel or the program exits
		 * with an exit message. 
		 * 
		 * Player vs Player - Tracks the keys pressed in the respective instance
		 * i.e. UP goes to keyPressed[0] etc. (See PvPPanel)
		 * 
		 * Player vs Comp - Tracks the keys pressed in the respective instance
		 * but ignores W and S as the computer controls the other panel. (See
		 * PvEPanel)
		 */
		
		int i = key.getKeyCode(); //Tracks the last key pressed
		
		if (currentPanel == "base"){ 
			//Switches the cursor location if UP or DOWN was pressed
			if (i == KeyEvent.VK_UP | i == KeyEvent.VK_DOWN){
				base.selected = 1 - base.selected;
				base.repaint();
			}	
			//Switches panels if ENTER was pressed
			if (i == KeyEvent.VK_ENTER){
				jframe.remove(base);
				if (base.selected == 0){
					//PvP was selected
					jframe.add(pvp = new PvPPanel());
					currentPanel = "pvp";
				}else{
					//PvE was selected
					jframe.add(pve = new PvEPanel());
					currentPanel = "pve";
				}
				jframe.revalidate();
				timer.start(); //Starts the auto frame update
			}
		}
		
		if (currentPanel == "endscreen"){
			//Switches the cursor location if UP or DOWN was pressed
			if (i == KeyEvent.VK_UP | i == KeyEvent.VK_DOWN){
				endScreen.selected = 1 - endScreen.selected;
				endScreen.repaint();
			}	
			//Switches panels if ENTER was pressed and "Play Again" is selected
			//Ends the game if "Quit" was selected
			if (i == KeyEvent.VK_ENTER){
				jframe.remove(endScreen);
				if (endScreen.selected == 0){
					jframe.add(base = new BaseNavigationPanel());
					jframe.revalidate();
					currentPanel = "base";
				}else{
					System.out.println("Thanks for playing!!");
					System.exit(0);
				}
			}
		}
		
		if (currentPanel == "pvp"){
				switch (i){
					//Tracking which key was pressed
					case KeyEvent.VK_UP:
						pvp.keysPressed[0]=true;
						break;
					case KeyEvent.VK_DOWN:
						pvp.keysPressed[1]=true;
						break;
					case KeyEvent.VK_W:
						pvp.keysPressed[2]=true;
						break;
					case KeyEvent.VK_S:
						pvp.keysPressed[3]=true;
						break;
				}
		}
		
		if (currentPanel == "pve"){
			switch (i){
				//Tracking which key was pressed
				case KeyEvent.VK_UP:
					pve.keysPressed[0]=true;
					break;
				case KeyEvent.VK_DOWN:
					pve.keysPressed[1]=true;
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		/*keyReleased(KeyEvent key):
		 * Called when a key is released. For PvPPanel and PvEPanel only.
		 * Sets the keys released to false in the respective instances.
		 * i.e. Releasing UP caused the program to set keyPressed[0] to false.
		 */
		int i = key.getKeyCode();//Gets the key released
		
		if (currentPanel == "pvp"){
			switch (i){
			//Sets the respective key to false to stop movement of paddles.
			case KeyEvent.VK_UP:
				pvp.keysPressed[0]=false;
				break;
			case KeyEvent.VK_DOWN:
				pvp.keysPressed[1]=false;
				break;
			case KeyEvent.VK_W:
				pvp.keysPressed[2]=false;
				break;
			case KeyEvent.VK_S:
				pvp.keysPressed[3]=false;
				break;
			}
		}
		
		if (currentPanel == "pve"){
			switch (i){
			//Sets the respective key to false to stop movement of paddles.
			case KeyEvent.VK_UP:
				pve.keysPressed[0]=false;
				break;
			case KeyEvent.VK_DOWN:
				pve.keysPressed[1]=false;
				break;
			case KeyEvent.VK_W:
				pve.keysPressed[2]=false;
				break;
			case KeyEvent.VK_S:
				pve.keysPressed[3]=false;
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
		/*keyTyped(KeyEvent key):
		 * Called when a key is typed. Used to pause game by starting
		 * and stopping the timer whenever "p" is typed.
		 */
		char p = key.getKeyChar(); //Sets p to the current key typed. 
		if (p == 'p'){
			//Based on the current state of paused,starts or stops the timer.
			if (paused){
				timer.start();
			}else{
				timer.stop();
			}
			paused = !paused; //Reverses Paused
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		/*actionPerformed(ActionEvent arg0):
		 * Called by Timer whenever it elapses. Checks to see which 
		 * panel is active to use the appropriate instance. 
		 * Acts as frame update by moving ball and paddles as well
		 * as testing to see if the game has ended in order to move
		 * the game to the end screen.
		 */
		
		if (currentPanel == "pvp"){
			boolean done = pvp.updateBall(); 
			pvp.updatePaddles();
			pvp.increaseBallSpeed();
			if (done){
				//Removes the panel and replaces it with EndScreenPanel
				jframe.remove(pvp);
				jframe.add(endScreen = new EndScreenPanel());
				jframe.revalidate();
				pvp = null;
				currentPanel = "endscreen";
				timer.stop();
			}
		}
		
		if (currentPanel == "pve"){
			boolean done = pve.updateBall();
			pve.updatePaddles();
			pve.increaseBallSpeed();
			if (done){
				//Removes the panel and replaces it with EndScreenPanel
				jframe.remove(pve);
				jframe.add(endScreen = new EndScreenPanel());
				jframe.revalidate();
				pve = null;
				currentPanel = "endscreen";
				timer.stop();
			}
		}
		//Calls repaint to refresh screen. 
		jframe.repaint();
	}
	
}
