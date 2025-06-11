import java.awt.Color;



import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Spells extends Rectangle {
		int num;
		int xSpeed;
		int ySpeed;
	Spells(int x, int y, int width, int height, int num){
		super(x,y,width,height);
		this.num = num;
	}
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
	
	public void move() {
		
		x+=xSpeed;
		y+=ySpeed;
		
	
	}
	
//could create a method if i want to decrease predictability of spell motion such as the one in player class

	public void mousePressed(MouseEvent e) {
		if(num==1) {
		xSpeed = 5;
		ySpeed = 0;
		}
		if(num == 2) {
			xSpeed = -5;
			ySpeed = 0;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	//create a drawShield method but put different id numbers
	//so when i press the key there is no conflict between the draw methods
	
	public void draw(Graphics g) {
		if(num==1) {
		g.setColor(Color.YELLOW);
		g.fillOval(x,y,width,height);
		}
		if(num==2) {
			g.setColor(Color.ORANGE);
			g.fillOval(x,y,width,height);
		}
		
		if(num == 3) {
			g.setColor(Color.green);
			g.fillOval(x,y,width,height);
		}
		
	}
	
}
