import javax.swing.*;





import java.awt.event.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GamePanel panel;  // Declare the panel
    JPanel startPanel;
    InstructionPanel Instructions;
    SinglePlayerPanel singlePlayer;

    public GameFrame() {
        // Initialize components
        startPanel = new JPanel();
        Instructions = new InstructionPanel(); // Make sure InstructionPanel class is defined properly
        singlePlayer = new SinglePlayerPanel();
        setupStartPanel();
        startPanel.setPreferredSize(new Dimension(1000, 500));

        // Initialize the panel and pass the listener for game over
        panel = new GamePanel();  // Initialize here
        panel.setGameOverListener(() -> returnToStartPanel());

        this.add(startPanel);

        this.setTitle("Wizard Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void setupStartPanel() {
        startPanel.setLayout(null);
        startPanel.setBackground(Color.BLUE);

        // Play Button
        JButton playButton = new JButton("Multi-Player");
        playButton.setFont(new Font("Consolas", Font.BOLD, 40));
        playButton.setFocusPainted(false);
        playButton.setBackground(Color.GREEN);
        playButton.setBounds(150, 100, 300, 100);
        playButton.addActionListener(e -> startGame());

        // Instructions Button
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.setFont(new Font("Consolas", Font.BOLD, 20));
        instructionsButton.setFocusPainted(false);
        instructionsButton.setBackground(Color.YELLOW);
        instructionsButton.setBounds(150, 250, 200, 150);
        instructionsButton.addActionListener(e -> showInstructions());
        
        JButton singlePlayerButton = new JButton("Single-Player");
        singlePlayerButton.setFont(new Font("Consolas", Font.BOLD, 20));
        singlePlayerButton.setFocusPainted(false);
        singlePlayerButton.setBackground(Color.ORANGE);
        singlePlayerButton.setBounds(500, 100, 300, 100);
        singlePlayerButton.addActionListener(e -> singlePlayerGame());
        
        // Add buttons to the start panel
        startPanel.add(playButton);
        startPanel.add(instructionsButton);
        startPanel.add(singlePlayerButton);
    }
   
    private void startGame() {
        remove(startPanel);

        if (panel != null) {
            panel.stopGame();  // Stop any previous game before starting a new one
        }

        // Recreate the GamePanel with the GameOverListener
        panel = new GamePanel();  // Reinitialize the panel here
        panel.setGameOverListener(() -> returnToStartPanel());

        add(panel);
        panel.requestFocus();
        revalidate();
        repaint();
    }
    
    private void singlePlayerGame() {
    		remove(startPanel);
    		add(singlePlayer);
    		singlePlayer.requestFocus();
    		revalidate();
    		repaint();
    }

    private void showInstructions() {
        remove(startPanel);
        createBackButton();
        add(Instructions);
        revalidate();
        repaint();
    }

    private void returnToStartPanel() {
        remove(panel);
        add(startPanel);
        startPanel.requestFocus();
        revalidate();
        repaint();
    }

    private void createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Consolas", Font.BOLD, 40));
        backButton.setFocusPainted(false);
        backButton.setBackground(Color.RED);
        backButton.setBounds(150, 400, 200, 100);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove instructions and show the start panel again
                remove(Instructions);
                add(startPanel);
                backButton.setVisible(false);  // Hide the back button once it's clicked
                revalidate();
                repaint();
            }
        });

        // Add back button to the frame, and make sure it's added only when necessary
        this.add(backButton);
    }
}

