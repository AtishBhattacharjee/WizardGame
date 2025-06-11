import javax.swing.*;









import java.awt.*;

public class InstructionPanel extends JPanel {
JButton backButton;
JPanel testingPanel;

	InstructionPanel(){
		this.setPreferredSize(new Dimension(1000,500));
		this.setBackground(Color.GREEN);
		this.setLayout(null);
	}
	
	public void draw(Graphics g) {
		 g.setFont(new Font("Consolas",Font.PLAIN,20));
		 g.drawString("Instructions for playing the game", 50, 50);
		 g.drawString("To move the wizards, use WASD and arrow keys",0,100);
		 g.drawString("To shoot spells, click the mouse",0,150);
		 g.drawString("Avoid getting hit by the spells and kill the", 0,200);
		 g.drawString("other player. Spells and players will",0,250);
		 g.drawString("disappear after collision.",0,300);
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	
	

	

}
