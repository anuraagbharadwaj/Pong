package pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EndScreenPanel extends JPanel {
	/* EndScreenPanel:
	 * Author: Anuraag Bharadwaj 
	 * Year: 2016
	 * Super Class: JPanel
	 * Purpose: End screen after game ends. Displays "Game Over"
	 * at the top of the screen. Also, displays two options for 
	 * the player: "Play Again" or "Quit"
	 * Player alternate options with up and down keys
	 * If Play Again is selected, BaseNavigation Panel is added
	 * If Quit is selected, it exits the program with a goodbye message
	 * 
	 * Includes:
	 * EndScreenPanel(): Creates an EndScreenPanel with selected set to
	 * 0 by default.
	 * 
	 * void paintComponent(Graphics g): Overrides JPanel.paintComponent(Graphics g)
	 * Draws in text and figure to indicate current selection.
	*/

	//selected has value of 0 or 1 for Play Again and Quit respectively
	public int selected;
	
	public EndScreenPanel(){
		//Constructor: No parameters
		//Creates a JPanel with selected set to 0 (Play Again) and 
		//sets its size to 801x480
		super();
		selected = 0;
		this.setPreferredSize(new Dimension(801,480));
	}
	
	@Override
	protected void paintComponent(Graphics g){
		/*paintComponent(Graphics g)
		 * Called whenever JComponent.repaint() is called
		 * Overrides paintCompenent from JComponent. Prints "Game Over" at the top
		 * Also prints "Play Again" and "Quit"
		 * Based on selected, a triangle is painted on the screen
		 * pointing to corresponding option in the menu. 
		 */
		
		//Calls JPanel.paintComponent
		super.paintComponent(g);
		
		//Creates the white background and top and bottom borders
		g.setColor(Color.black);
		this.setBackground(Color.white);
		for(int i = 0;i<3;i++){
			g.drawLine(0, i, 801, i);
			g.drawLine(0, 480-i, 801, 480-i);
		}
		
		//Creates two font in bold with sizes of 24 and 18
		Font font = new Font(g.getFont().getFontName(),Font.BOLD,24);
		Font font2 = new Font(g.getFont().getFontName(),Font.BOLD,48);
		g.setFont(font2);
		
		//Draws in the texts in the appropriate locations
		g.drawString("Game Over", 300, 200);
		g.setFont(font);
		g.drawString("Play Again?", 300, 275);
		g.drawString("Quit", 300, 335);
		
		//Draws the triangle that points to the selected option
		int[] markerX = {260,260,280};
		int[] markerY = {258+60*selected,278+60*selected,268+60*selected};
		g.fillPolygon(markerX, markerY, 3);
	}
	
}
