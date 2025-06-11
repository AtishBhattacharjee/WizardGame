import java.awt.*;
import java.awt.event.*;

public class SpellDuel {
    private int startA, startB;
    private int strengthA, strengthB;
    private int beamPosition;
    private boolean collide = false;

    private int rect1width = 10;
    private int rect2width = 10;
    
    private boolean pressingE = false;
    private boolean pressingP = false;

    public SpellDuel(int startA, int startB) {
        this.startA = startA;
        this.startB = startB;
        this.beamPosition = (startA + startB) / 2;
    }

    public void setStrengths(int a, int b) {
        this.strengthA = a;
        this.strengthB = b;
    }

    public void updateDuel() {
        int total = strengthA + strengthB;
        if (total == 0) return;

        int push = strengthB - strengthA;
        beamPosition += push / 5;

        // Clamp
        beamPosition = Math.max(startA, Math.min(startB, beamPosition));
    }
    
    public void update() {
    	if(pressingE)rect1width+=2;
    	if(pressingP)rect2width+=2;
    }
    
    public void keyPressed(KeyEvent e) {
    	if(e.getKeyCode()==KeyEvent.VK_E) {
    		
    		pressingE = true;
    	}
    	if(e.getKeyCode()==KeyEvent.VK_P) {
    	
    		pressingP = true;
    	}
    	checkCollision();
    }
    
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E) {
            pressingE = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            pressingP = false;
        }
    }
    
    public void checkCollision() {
    	if(100+rect1width>=900-rect2width) {
    		collide = true;
    	}
    }
    

    public void drawDuel(Graphics g) {
        if (collide) {
            g.setColor(Color.BLUE);
            g.fillRect(startA+50 , 350, beamPosition - startA, 10);

            g.setColor(Color.RED);
            g.fillRect(beamPosition, 350, startB - beamPosition, 10);

            g.setColor(Color.YELLOW);
            g.fillOval(beamPosition - 5, 345, 10, 20);
        } else {
            // BLUE spell from left
            g.setColor(Color.BLUE);
            g.fillRect(100, 350, rect1width, 10); // starts at 100, grows to the right

            // RED spell from right
            g.setColor(Color.RED);
            g.fillRect(900-rect2width, 350, rect2width, 10); // starts at 850 - width, grows to the left
        }
    }
}

