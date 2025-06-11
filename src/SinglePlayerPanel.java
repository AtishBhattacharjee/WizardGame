import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
public class SinglePlayerPanel extends JPanel implements Runnable {
	  private Background background = new Background(0, 0, 1000, 500);
	  
	    private Player player1 = new Player(900, 400, 50, 50, 1);
	
	    private Player testPlayer = new Player(500,200,50,50,3);
	    
	    private Thread thread;
	    private boolean running = true;
	    private int player1LifeCount = 3;
	  
	    private int testPlayerLifeCount = 5;
	    
	    private Timer timer;
	    private Timer spellTimer; 
	    
	    private ArrayList<Spells> spellList = new ArrayList<>();
	    private ArrayList<Spells> spell1List = new ArrayList<>();
	    
	    private GameOverListener gameOverListener;
	    Random random = new Random();
	    
	    private boolean shieldActive = false;
	    
	    private int xShieldLoc = player1.x-25;
	    private int yShieldLoc = player1.y-75;
	    Shield shield = new Shield(xShieldLoc, yShieldLoc, 100,100,1);

	   
	    int spellTime = random.nextInt(1000) + 100;
	
	
	    public SinglePlayerPanel() {
	    	this.setPreferredSize(new Dimension(1000, 500));
	    	this.setFocusable(true);
	    	this.addKeyListener(new Keys());
	    	this.addMouseListener(new Mouse());

	    	resetGame();
    
	    	thread = new Thread(this);
	    	thread.start();

	    	timer = new Timer(3000, e -> revive());
	    	timer.setRepeats(true);
	    	timer.start();
    
	    	spellTimer = new Timer(spellTime, e -> createSpell1(testPlayer.x,testPlayer.y, 5,0,3));
	    	spellTimer.setRepeats(true);
	    	spellTimer.start();
	    }
	    public void setGameOverListener(GameOverListener listener) {
	        this.gameOverListener = listener;
	    }

	
	    private void resetGame() {
	        player1LifeCount = 3;
	      
	        testPlayerLifeCount = 5;
	        spellList.clear();
	        spell1List.clear();
	      
	        player1.resetPosition();
	        testPlayer.resetPosition();
	        
	        updateShieldPosition();
	        
	    }
	    private void updateShieldPosition() {
	    	shield.x = xShieldLoc;
	    	shield.y = yShieldLoc;
	   
	    	
	    }
	
	    public void revive() {
	        player1.isAlive = true;
	        testPlayer.isAlive = true; 
	    }
	
	    public void draw(Graphics g) {
	        background.draw(g);
	        g.setColor(Color.green);
	        g.fillRect(0, 400, 1000, 400);
	        player1.draw(g);
	       
	        g.setFont(new Font("Consolas", Font.PLAIN, 50));
	        g.drawString(Integer.toString(player1LifeCount),900,100);
	        g.drawString(Integer.toString(testPlayerLifeCount),500,100);
	        		
	        testPlayer.draw(g);
	        
	        if(shieldActive) {
	        shield.updatePosition(xShieldLoc, yShieldLoc);
	        	
	        shield.draw(g);
	        }
	        
	 
	       
	        
	        
	        for (Spells spell : spellList) {
	            spell.draw(g);
	        }
	        for (Spells spell1 : spell1List) {
	            spell1.draw(g);
	        }
	        
	  

	        if (player1LifeCount == 0 ||testPlayerLifeCount == 0) {
	            g.setFont(new Font("Consolas", Font.PLAIN, 50));
	            g.drawString("Game Over!", 200, 100);

	            if (gameOverListener != null) {
	                gameOverListener.onGameOver();
	            }
	        }
	    }	
	    
	    
	    
	    public void move() {
	        player1.move();
	        testPlayer.move();
	        
	        if(shieldActive) {
	        	 xShieldLoc = player1.x + player1.width / 2 - shield.width / 2;
	             yShieldLoc = player1.y + player1.height / 2 - shield.height / 2;
	        	shield.updatePosition(xShieldLoc, yShieldLoc);
	        }
	        
	
	        for (Spells spell : spellList) {
	            spell.move();
	        }
	        for (Spells spell1 : spell1List) {
	            spell1.move();
	        }
	        
	    
	    }
	
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        draw(g);
	    }
	    
	    public void createSpell(int x, int y, int speedX, int speedY, int num) {
	        Spells newSpell = new Spells(x, y, 10, 10, num);
	        newSpell.xSpeed = speedX;
	        newSpell.ySpeed = speedY;
	        spellList.add(newSpell);
	    }

	    public void createSpell1(int x, int y, int speedX, int speedY, int num) {
	        Spells newSpell1 = new Spells(x, y, 10, 10, num);
	        newSpell1.xSpeed = -speedX;
	        newSpell1.ySpeed = speedY;
	        spell1List.add(newSpell1);
	    }
	
	    
	    public class Keys extends KeyAdapter {
	        public void keyPressed(KeyEvent e) {
	            player1.keyPressed(e);
	            
	        
	            
	            if(e.getKeyCode()==KeyEvent.VK_M) {
	            	shieldActive = true;
	            }
	            
	            }

	        public void keyReleased(KeyEvent e) {
	            player1.keyReleased(e);
	            
	           
	       
	            
	            if(e.getKeyCode()==KeyEvent.VK_M) {
	            	shieldActive = false;
	            }
	            
	        }
	    }
	    
	    
	    public void checkCollision() {
	        // **Check if player1's y position is out of bounds (on the ground)**
	        if (player1.y + player1.width >= 400) {
	            player1.y = 400 - player1.width;  // Adjust the position to prevent overlap
	        }
	   
	        
	        if(testPlayer.y + testPlayer.width >= 400) {
	        	testPlayer.y = 400 - testPlayer.width;
	        }
	        if(testPlayer.y <= 0) {
	        	testPlayer.y = 0;
	        }
	        if(testPlayer.x <= 0) {
	        	testPlayer.x = 0;
	        }
	        if(testPlayer.x + testPlayer.width >= 1000) {
	        	testPlayer.x = 1000 - testPlayer.width;
	        }

	        // **Collision detection between spells**
	        ArrayList<Spells> toRemoveSpells = new ArrayList<>();
	        ArrayList<Spells> toRemoveSpells1 = new ArrayList<>();
	     

	        // **Check spell collisions first**
	        for (Spells spell : spellList) {
	            for (Spells spell1 : spell1List) {
	                if (spell.getBounds().intersects(spell1.getBounds())) {
	                    toRemoveSpells.add(spell);
	                    toRemoveSpells1.add(spell1);
	                }
	            }
	        }
	        
	    
	        

	       // spell hits player collision 
	       
	        for (Spells spell1 : spell1List) {
	            if (spell1.getBounds().intersects(player1.getBounds())) {
	                player1.die();  // Player1 should die immediately when hit
	                toRemoveSpells1.add(spell1);
	                player1LifeCount--;  // Decrease Player1's life count
	            }
	        }
	        
	        for (Spells spell : spellList) {
	            if (spell.getBounds().intersects(testPlayer.getBounds())) {
	                testPlayer.die();  // Player1 should die immediately when hit
	                toRemoveSpells.add(spell);
	                testPlayerLifeCount--;  // Decrease Player1's life count
	            }
	        }
	        
	 
	      
	 
	        
	         // add a condition for if m is pressed 
	    if(shieldActive) {
	        for(Spells spell1: spell1List) {
	       	if(spell1.getBounds().intersects(shield.getBounds())) {
	       		toRemoveSpells1.add(spell1);
	        	
	       	}
	       }
	       
	        
	   
	     
	      }
	        spellList.removeAll(toRemoveSpells);
	        spell1List.removeAll(toRemoveSpells1);
	       
	        
}
	      
	       

	    public class Mouse extends MouseAdapter {
	        public void mousePressed(MouseEvent e) {
	            if (e.getButton() == MouseEvent.BUTTON1) {
	                createSpell(player1.x + player1.width - 5, player1.y, -5, 0, 1);
	            }
	         
	     
	        }
	    }
	       

	   
	       
	    
	    
	public void run() {
		 long lastTime = System.nanoTime();
	        double ns = 1000000000 / 60.0;
	        double delta = 0;

	        while (running) {
	            long now = System.nanoTime();
	            delta += (now - lastTime) / ns;
	            lastTime = now;

	            if (delta >= 1) {
	                move();
	                repaint();
	                checkCollision();
	                delta--;
	            }
	        }
		
	}
	
    public void stopGame() {
        running = false;
    }
    
    public interface GameOverListener{
    	void onGameOver();
    }
	

}
