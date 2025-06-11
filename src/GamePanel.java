import javax.swing.*;






import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;

public class GamePanel extends JPanel implements Runnable,ActionListener{
    private Background background = new Background(0, 0, 1000, 500);
    private Player player1 = new Player(900, 400, 50, 50, 1);
    private Player AI_Player = new Player(50, 400, 50, 50, 2);
 
    private Thread thread;
    private boolean running = true;
    private int player1LifeCount = 3;
    private int AI_PlayerLifeCount = 3;
  
    private Timer timer;
    private Timer spellTimer; 
    
    private ArrayList<Spells> spellList = new ArrayList<>();
    private ArrayList<Spells> spell1List = new ArrayList<>();
    private HashSet<Integer> keysPressed;
    private SpellDuel duel;
    private GameOverListener gameOverListener;
    Random random = new Random();
    
    private boolean shieldActive = false;
    private boolean AIShieldActive = false;
    private int xShieldLoc = player1.x-25;
    private int yShieldLoc = player1.y-75;
    Shield shield = new Shield(xShieldLoc, yShieldLoc, 100,100,1);
    private int AIShieldX = AI_Player.x-25;
    private int AIShieldY = AI_Player.y-75;
    Shield AI_Shield = new Shield(AIShieldX, AIShieldY,100,100,2);
   
    int spellTime = random.nextInt(1000) + 100;

    public GamePanel() {
        this.setPreferredSize(new Dimension(1000, 500));
        this.setFocusable(true);
        this.addKeyListener(new Keys());
        this.addMouseListener(new Mouse());
        
        keysPressed = new HashSet<>();
        duel = new SpellDuel(AI_Player.x,player1.x);
        
        resetGame();

        thread = new Thread(this);
        thread.start();

        timer = new Timer(3000, e -> revive());
        timer.setRepeats(true);
        timer.start();
        spellTimer = new Timer(100,this);
        spellTimer.start();
        Timer duelTimer = new Timer(16, new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		duel.update();
        		repaint();
        	}
        });
        duelTimer.start();
       
    }
    
    public void actionPerformed(ActionEvent e) {
    	
    	
    	int strengthA = keysPressed.contains(KeyEvent.VK_E)?10:0;
    	int strengthB = keysPressed.contains(KeyEvent.VK_P)?10:0;
    	
    	duel.setStrengths(strengthA, strengthB);
    	duel.updateDuel();
    	repaint();
    	
    }

    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    private void resetGame() {
        player1LifeCount = 3;
        AI_PlayerLifeCount = 3;
       
        spellList.clear();
        spell1List.clear();
     
        player1.resetPosition();
        AI_Player.resetPosition();
        
        updateShieldPosition();
        
    }
    
    private void updateShieldPosition() {
    	shield.x = xShieldLoc;
    	shield.y = yShieldLoc;
    	AI_Shield.x = AIShieldX;
    	AI_Shield.y = AIShieldY;
    	
    }

    public void revive() {
        player1.isAlive = true;
        AI_Player.isAlive = true;
      
    }

    public void draw(Graphics g) {
    	
        background.draw(g);
        g.setColor(Color.green);
        g.fillRect(0, 400, 1000, 400);
        player1.draw(g);
        AI_Player.draw(g);
       
        g.setFont(new Font("Consolas", Font.PLAIN, 50));
        g.drawString(Integer.toString(AI_PlayerLifeCount),100,100);
        g.drawString(Integer.toString(player1LifeCount),900,100);
     
        
        if(shieldActive) {
        shield.updatePosition(xShieldLoc, yShieldLoc);
        	
        shield.draw(g);
        }
        
        if(AIShieldActive) {
        	AI_Shield.updatePosition(AIShieldX, AIShieldY);
        	
        	AI_Shield.draw(g);
        }
       
        
        
        for (Spells spell : spellList) {
            spell.draw(g);
        }
        for (Spells spell1 : spell1List) {
            spell1.draw(g);
        }
        
    

        if (player1LifeCount == 0 || AI_PlayerLifeCount == 0) {
            g.setFont(new Font("Consolas", Font.PLAIN, 50));
            g.drawString("Game Over!", 200, 100);

            if (gameOverListener != null) {
                gameOverListener.onGameOver();
            }
        }
    }

    public void move() {
        player1.move();
        AI_Player.move();
        
        
        if(shieldActive) {
        	 xShieldLoc = player1.x + player1.width / 2 - shield.width / 2;
             yShieldLoc = player1.y + player1.height / 2 - shield.height / 2;
        	shield.updatePosition(xShieldLoc, yShieldLoc);
        }
        
        if(AIShieldActive) {
        	AIShieldX = AI_Player.x + AI_Player.width / 2 - AI_Shield.width / 2;
        	AIShieldY = AI_Player.y + AI_Player.height / 2 - AI_Shield.height / 2;
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
        duel.drawDuel(g);
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
            
            AI_Player.keyPressed(e);
            
            if(e.getKeyCode()==KeyEvent.VK_M) {
            	shieldActive = true;
            }
            
            if(e.getKeyCode()==KeyEvent.VK_L) {
            	AIShieldActive = true;
            }
            
            
          keysPressed.add(e.getKeyCode());
            
          duel.keyPressed(e);
           
        }

        public void keyReleased(KeyEvent e) {
            player1.keyReleased(e);
            
           
            AI_Player.keyReleased(e);
            
            if(e.getKeyCode()==KeyEvent.VK_M) {
            	shieldActive = false;
            }
            
            if(e.getKeyCode()==KeyEvent.VK_L) {
            	AIShieldActive = false;
            }
         keysPressed.remove(e.getKeyCode());
        }
        
        
     
    }

    public void checkCollision() {
        // **Check if player1's y position is out of bounds (on the ground)**
        if (player1.y + player1.width >= 400) {
            player1.y = 400 - player1.width;  // Adjust the position to prevent overlap
        }
        // **Check if AI_Player's y position is out of bounds (on the ground)**
        if (AI_Player.y + AI_Player.width >= 400) {
            AI_Player.y = 400 - AI_Player.width;  // Adjust the position to prevent overlap
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
       
        for (Spells spell : spellList) {
            if (spell.getBounds().intersects(player1.getBounds())) {
                player1.die();  // Player1 should die immediately when hit
                toRemoveSpells.add(spell);
                player1LifeCount--;  // Decrease Player1's life count
            }
        }
        

 
      
        for (Spells spell1 : spell1List) {
            if (spell1.getBounds().intersects(AI_Player.getBounds())) {
                AI_Player.die();  // AI_Player should die immediately when hit
                toRemoveSpells1.add(spell1);
                AI_PlayerLifeCount--;  // Decrease AI_Player's life count
            }
        }
        
   
        
  
 
        
        
        
        // add a condition for if m is pressed 
    
        if(shieldActive) {
        for(Spells spell: spellList) {
       	if(spell.getBounds().intersects(shield.getBounds())) {
       		toRemoveSpells.add(spell);
        	
       	}
       }
        
    
}
        if(AIShieldActive) {
        	for(Spells spell1 : spell1List) {
        	if(spell1.getBounds().intersects(AI_Shield.getBounds())) {
        		toRemoveSpells1.add(spell1);
        	}
        	}
        	
}
        spellList.removeAll(toRemoveSpells);
        spell1List.removeAll(toRemoveSpells1);
      }
        
   
        

        // **Remove all collided spells after checking everything**
       
       

   
    


    public class Mouse extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                createSpell(AI_Player.x + AI_Player.width - 5, AI_Player.y, 5, 0, 1);
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                createSpell1(player1.x, player1.y, 5, 0, 2);
            }
     
        }
    }

    @Override
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

