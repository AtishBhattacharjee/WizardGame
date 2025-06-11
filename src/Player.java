import java.awt.Color;





import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import java.util.Random;
import java.util.Timer;



public class Player extends Rectangle{
	int xSpeed;
	int ySpeed;
	int num;
	boolean isAlive = true; 
	Image player1WizardImage;
	Image AIPlayerWizardImage;
	Image testPlayerImage;
	public int startX, startY;
	Random random = new Random();
	
	int randomXSpeed;
	int randomYSpeed; 
	private long lastDirectionChangeTime = System.currentTimeMillis();
	
Player(int x, int y, int width, int height, int num){
	super(x,y,width,height);
	this.num = num;
	player1WizardImage = new ImageIcon("./Image/WizardPlayer.png").getImage();
	AIPlayerWizardImage = new ImageIcon("./Image/AIWizardPlayer.png").getImage();
	testPlayerImage = new ImageIcon("./Image/wizardImage.jpg").getImage(); //add image here
	this.startX = x;
	this.startY = y;
	
}

public void resetPosition() {
	this.x = startX;
	this.y = startY;
	
}

public Rectangle getBounds() {
	return new Rectangle(x,y,width,height);
}

public void die() {
	isAlive = false;
	xSpeed = 0;
	ySpeed = 0;
}
public void move() {
	if(num == 3) testPlayerMotion();
	if(isAlive) {
	x+=xSpeed;
	y+=ySpeed;
	}
	
}

public void testPlayerMotion() {
	long now = System.currentTimeMillis();
	if(now-lastDirectionChangeTime > 1000) {
	 randomXSpeed = random.nextInt(11)-5;
	 randomYSpeed = random.nextInt(11)-5;
	 lastDirectionChangeTime = now;
	}
		xSpeed = randomXSpeed;
		ySpeed = randomYSpeed;
	
}

public void keyPressed(KeyEvent e) {
	if(!isAlive) {
		return;
	}
	switch(num) {
	case 1:
	if(e.getKeyCode()==KeyEvent.VK_UP) {
		ySpeed = -5;
	}
	if(e.getKeyCode()==KeyEvent.VK_DOWN) {
		ySpeed = 5;
	}
	if(e.getKeyCode()==KeyEvent.VK_LEFT) {
		xSpeed = -5;
	}
	if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
		xSpeed = 5;
	}
	break;
	
	case 2:
	if(e.getKeyCode()==KeyEvent.VK_W) {
		ySpeed = -5;
	}
	if(e.getKeyCode()==KeyEvent.VK_S) {
		ySpeed = 5;
	}
	
	if(e.getKeyCode()==KeyEvent.VK_A) {
		xSpeed = -5;
	}
	if(e.getKeyCode()==KeyEvent.VK_D) {
		xSpeed = 5;
	}
	
	break;
	
	}
	
}
public void keyReleased(KeyEvent e) {
	if(!isAlive) {
		return;
	}
	switch(num) {
	case 1:
	if(e.getKeyCode()==KeyEvent.VK_UP) {
		ySpeed = 0;
	}
	if(e.getKeyCode()==KeyEvent.VK_DOWN) {
		ySpeed = 0;
	}
	if(e.getKeyCode()==KeyEvent.VK_LEFT) {
		xSpeed = 0;
	}
	if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
		xSpeed = 0;
	}
	break;
	
	// same goes for here
	
	case 2:
		if(e.getKeyCode()==KeyEvent.VK_W) {
			ySpeed = 0;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_S) {
			ySpeed = 0;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_A) {
			xSpeed = 0;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_D) {
			xSpeed = 0;
		}
	
	
	}
}

public void draw(Graphics g) {
	if(!isAlive) {
		return;
	}
	if(num==1) {
	g.drawImage(player1WizardImage, x,y,width,height, null);
	}
	if(num==2) {
		
		g.drawImage(AIPlayerWizardImage,x,y,width,height,null);
		
	}
	
	if(num == 3) {
	
		g.drawImage(testPlayerImage, x, y, width, height, null);
	}

}
}
