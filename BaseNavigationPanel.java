package pong;

import java.awt.*;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BaseNavigationPanel extends JPanel {
	/* BaseNavigationPanel:
	 * Author: Anuraag Bharadwaj
	 * Year: 2016 
	 * Super Class: JPanel
	 * Purpose: Provides the base navigation for Pong.
	 * Displays two options for the player to select from 
	 * Player vs Player or Player vs Computer for each of the appropriate
	 * game modes (2 player or 1 player vs an AI)
	 * Player can alternate between options with the up and down keys
	 * Once an option is selected, the panel is removed and replaced
	 * with the appropriate panel.
	 * 
	 * Includes: 
	 * BaseNagivationPanel(): Creates a BaseNavigationPanel with selected
	 * set to 0 by default
	 * paintComponent(Graphics g): Overrides JPanel.paintComponent(Graphics g)
	 * Draws in text and figure indicating current option
	*/
	
	//selected has values 0 or 1, 0 corresponds to PvP and 1 to PvE
	public int selected; 
	
	public BaseNavigationPanel(){
		//Constructor: No parameters
		//Creates a JPanel with selected set to 0 (PvP) and 
		//sets its size to 801x480
		super();
		selected = 0;
		this.setPreferredSize(new Dimension(801,480));
	}
	
	@Override
	protected void paintComponent(Graphics g){
		/*paintCompenent: Graphics g
		 * Called whenever JPanel.repaint() is called
		 * Overrides paintCompenent from JComponent. Prints "Pong" at the top
		 * Also prints "Player vs Player" and "Player vs Comp"
		 * Based on selected, a triangle is painted on the screen
		 * pointing to corresponding option in the menu.
		 */
		//Calls the JPanel's paintComponent
		super.paintComponent(g);
		
		//Creates the white background and top and bottom borders
		g.setColor(Color.black);
		this.setBackground(Color.white);
		for(int i = 0;i<3;i++){
			g.drawLine(0, i, 801, i);
			g.drawLine(0, 480-i, 801, 480-i);
		}
		
		//Creates two fonts with the same font name but different sizes 
		//and in the bold style
		Font font = new Font(g.getFont().getFontName(),Font.BOLD,24);
		Font font2 = new Font(g.getFont().getFontName(),Font.BOLD,48);
		g.setFont(font2);
		
		//Prints the words for the base navigation
		g.drawString("Pong", 300, 200);
		g.setFont(font);
		g.drawString("Player v Player", 300, 275);
		g.drawString("Player v Comp", 300, 335);
		
		//Draws the triangle that points to the selected option
		int[] markerX = {260,260,280};
		int[] markerY = {258+60*selected,278+60*selected,268+60*selected};
		g.fillPolygon(markerX, markerY, 3);
	}

}
